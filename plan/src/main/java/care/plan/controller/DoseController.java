package care.plan.controller;

import care.plan.enums.DoseStatus;
import care.plan.model.Dose;
import care.plan.service.DoseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DoseController {

    private final DoseService doseService;

    public DoseController(DoseService doseService) {
        this.doseService = doseService;
    }

    @PostMapping("/schedules/{scheduleId}/doses")
    public Dose createDose(
            @PathVariable Long scheduleId,
            @RequestParam LocalDateTime scheduledDateTime) {

        return doseService.createDose(
                scheduleId,
                scheduledDateTime);
    }

    @GetMapping("/schedules/{scheduleId}/doses")
    public List<Dose> getDosesBySchedule(
            @PathVariable Long scheduleId) {

        return doseService.getDosesBySchedule(scheduleId);
    }

    @GetMapping("/doses/{id}")
    public Dose getDoseById(
            @PathVariable Long id) {

        return doseService.getDoseById(id);
    }

    @PutMapping("/doses/{id}/status")
    public Dose updateDoseStatus(
            @PathVariable Long id,
            @RequestParam DoseStatus status) {

        return doseService.updateDoseStatus(
                id,
                status);
    }

    @GetMapping("/doses")
    public List<Dose> getDosesByDate(
            @RequestParam LocalDate date) {

        return doseService.getDosesByDate(date);
    }

    @GetMapping("/doses/overdue")
    public List<Dose> getOverdueDoses() {

        return doseService.getOverdueDoses();
    }
}