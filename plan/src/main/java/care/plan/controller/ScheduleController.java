package care.plan.controller;

import care.plan.model.Schedule;
import care.plan.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/medicines/{medicineId}/schedules")
    public Schedule createSchedule(
            @PathVariable Long medicineId,
            @RequestBody Schedule schedule) {

        return scheduleService.createSchedule(medicineId, schedule);
    }

    @GetMapping("/medicines/{medicineId}/schedules")
    public List<Schedule> getSchedulesByMedicine(
            @PathVariable Long medicineId) {

        return scheduleService.getSchedulesByMedicine(medicineId);
    }

    @GetMapping("/schedules/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {

        return scheduleService.getScheduleById(id);
    }

    @PutMapping("/schedules/{id}")
    public Schedule updateSchedule(
            @PathVariable Long id,
            @RequestBody Schedule schedule) {

        return scheduleService.updateSchedule(id, schedule);
    }

    @DeleteMapping("/schedules/{id}")
    public String deleteSchedule(@PathVariable Long id) {

        scheduleService.deleteSchedule(id);

        return "Schedule deleted successfully";
    }
}