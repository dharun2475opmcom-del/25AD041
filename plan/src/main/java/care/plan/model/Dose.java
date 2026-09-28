package care.plan.model;

import care.plan.enums.DoseStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Dose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    private LocalDateTime scheduledDateTime;

    @Enumerated(EnumType.STRING)
    private DoseStatus status;

    public Dose() {
    }

    public Dose(
            Schedule schedule,
            LocalDateTime scheduledDateTime,
            DoseStatus status) {

        this.schedule = schedule;
        this.scheduledDateTime = scheduledDateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public DoseStatus getStatus() {
        return status;
    }

    public void setStatus(DoseStatus status) {
        this.status = status;
    }
}