package care.plan.service;

import care.plan.enums.DoseLogStatus;
import care.plan.enums.DoseStatus;
import care.plan.exception.DoseLogAlreadyFinalizedException;
import care.plan.model.Dose;
import care.plan.model.DoseLog;
import care.plan.repository.DoseLogRepository;
import care.plan.repository.DoseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoseLogService {

    private final DoseLogRepository doseLogRepository;
    private final DoseRepository doseRepository;

    public DoseLogService(
            DoseLogRepository doseLogRepository,
            DoseRepository doseRepository) {

        this.doseLogRepository = doseLogRepository;
        this.doseRepository = doseRepository;
    }

    public DoseLog createDoseLog(
            Long doseId,
            DoseLogStatus status,
            String notes) {

        Dose dose = doseRepository.findById(doseId)
                .orElseThrow(() ->
                        new RuntimeException("Dose not found"));

        if (doseLogRepository.existsByDoseId(doseId)) {
            throw new RuntimeException(
                    "Dose log already exists");
        }

        if (dose.getStatus() != DoseStatus.PENDING) {
            throw new RuntimeException(
                    "Dose status has already been finalized");
        }

        DoseLog doseLog = new DoseLog();

        doseLog.setDose(dose);
        doseLog.setStatus(status);
        doseLog.setLoggedAt(LocalDateTime.now());
        doseLog.setNotes(notes);

        if (status == DoseLogStatus.TAKEN) {
            dose.setStatus(DoseStatus.TAKEN);
        } else if (status == DoseLogStatus.MISSED) {
            dose.setStatus(DoseStatus.MISSED);
        }

        doseRepository.save(dose);

        return doseLogRepository.save(doseLog);
    }

    public List<DoseLog> getAllDoseLogs() {
        return doseLogRepository.findAll();
    }

    public DoseLog getDoseLogById(Long id) {

        return doseLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Dose log not found"));
    }

    public DoseLog updateDoseLog(
            Long id,
            DoseLogStatus status,
            String notes) {

        throw new DoseLogAlreadyFinalizedException(
                "Dose log cannot be modified after creation");
    }

    public void deleteDoseLog(Long id) {

        DoseLog existingDoseLog = getDoseLogById(id);

        doseLogRepository.delete(existingDoseLog);
    }
}