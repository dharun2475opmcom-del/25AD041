package care.plan.service;

import care.plan.exception.DoseAlreadyFinalizedException;
import care.plan.enums.DoseStatus;
import care.plan.model.Dose;
import care.plan.model.Schedule;
import care.plan.repository.DoseRepository;
import care.plan.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DoseService {

    private final DoseRepository doseRepository;
    private final ScheduleRepository scheduleRepository;

    public DoseService(
            DoseRepository doseRepository,
            ScheduleRepository scheduleRepository) {

        this.doseRepository = doseRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Dose createDose(
            Long scheduleId,
            LocalDateTime scheduledDateTime) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new RuntimeException("Schedule not found"));

        if (scheduledDateTime.toLocalDate()
                .isBefore(schedule.getStartDate())) {

            throw new RuntimeException(
                    "Dose date is before schedule start date");
        }

        if (schedule.getEndDate() != null &&
                scheduledDateTime.toLocalDate()
                        .isAfter(schedule.getEndDate())) {

            throw new RuntimeException(
                    "Dose date is after schedule end date");
        }

        if (doseRepository.existsByScheduleIdAndScheduledDateTime(
                scheduleId,
                scheduledDateTime)) {

            throw new RuntimeException(
                    "Dose already exists for this scheduled time");
        }

        Dose dose = new Dose();

        dose.setSchedule(schedule);
        dose.setScheduledDateTime(scheduledDateTime);
        dose.setStatus(DoseStatus.PENDING);

        return doseRepository.save(dose);
    }

    public List<Dose> getDosesBySchedule(Long scheduleId) {

        scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new RuntimeException("Schedule not found"));

        return doseRepository.findByScheduleId(scheduleId);
    }

    public Dose getDoseById(Long id) {

        return doseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Dose not found"));
    }

    public Dose updateDoseStatus(
            Long id,
            DoseStatus status) {

        Dose dose = getDoseById(id);

        if (dose.getStatus() != DoseStatus.PENDING) {

            throw new DoseAlreadyFinalizedException(
                    "Dose status has already been finalized");
        }

        dose.setStatus(status);

        return doseRepository.save(dose);
    }

    public List<Dose> getDosesByDate(LocalDate date) {

        LocalDateTime start =
                date.atStartOfDay();

        LocalDateTime end =
                date.atTime(LocalTime.MAX);

        return doseRepository.findByScheduledDateTimeBetween(
                start,
                end);
    }

    public List<Dose> getOverdueDoses() {

        LocalDateTime overdueTime =
                LocalDateTime.now().minusHours(1);

        return doseRepository
                .findByScheduledDateTimeBetween(
                        LocalDateTime.MIN,
                        overdueTime)
                .stream()
                .filter(dose ->
                        dose.getStatus() == DoseStatus.PENDING)
                .toList();
    }
}