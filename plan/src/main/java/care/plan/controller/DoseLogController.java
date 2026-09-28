package care.plan.controller;

import care.plan.enums.DoseLogStatus;
import care.plan.model.DoseLog;
import care.plan.service.DoseLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DoseLogController {

    private final DoseLogService doseLogService;

    public DoseLogController(DoseLogService doseLogService) {
        this.doseLogService = doseLogService;
    }

    @PostMapping("/doses/{doseId}/logs")
    public DoseLog createDoseLog(
            @PathVariable Long doseId,
            @RequestParam DoseLogStatus status,
            @RequestParam(required = false) String notes) {

        return doseLogService.createDoseLog(
                doseId,
                status,
                notes);
    }

    @GetMapping("/dose-logs")
    public List<DoseLog> getAllDoseLogs() {
        return doseLogService.getAllDoseLogs();
    }

    @GetMapping("/dose-logs/{id}")
    public DoseLog getDoseLogById(
            @PathVariable Long id) {

        return doseLogService.getDoseLogById(id);
    }

    @PutMapping("/dose-logs/{id}")
    public DoseLog updateDoseLog(
            @PathVariable Long id,
            @RequestParam DoseLogStatus status,
            @RequestParam(required = false) String notes) {

        return doseLogService.updateDoseLog(
                id,
                status,
                notes);
    }

    @DeleteMapping("/dose-logs/{id}")
    public String deleteDoseLog(
            @PathVariable Long id) {

        doseLogService.deleteDoseLog(id);

        return "Dose log deleted successfully";
    }
}