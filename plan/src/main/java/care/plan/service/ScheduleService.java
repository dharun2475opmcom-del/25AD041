package care.plan.service;

import care.plan.model.Medicine;
import care.plan.model.Schedule;
import care.plan.repository.MedicineRepository;
import care.plan.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MedicineRepository medicineRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            MedicineRepository medicineRepository) {

        this.scheduleRepository = scheduleRepository;
        this.medicineRepository = medicineRepository;
    }

    public Schedule createSchedule(Long medicineId, Schedule schedule) {

        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        schedule.setMedicine(medicine);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesByMedicine(Long medicineId) {

        medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        return scheduleRepository.findByMedicineId(medicineId);
    }

    public Schedule getScheduleById(Long id) {

        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public Schedule updateSchedule(Long id, Schedule schedule) {

        Schedule existingSchedule = getScheduleById(id);

        existingSchedule.setFrequency(schedule.getFrequency());
        existingSchedule.setScheduledTime(schedule.getScheduledTime());
        existingSchedule.setStartDate(schedule.getStartDate());
        existingSchedule.setEndDate(schedule.getEndDate());

        return scheduleRepository.save(existingSchedule);
    }

    public void deleteSchedule(Long id) {

        Schedule existingSchedule = getScheduleById(id);

        scheduleRepository.delete(existingSchedule);
    }
}