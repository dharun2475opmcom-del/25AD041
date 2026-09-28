package care.plan.model;

import care.plan.enums.DoseLogStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DoseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "dose_id", nullable = false, unique = true)
    private Dose dose;

    @Enumerated(EnumType.STRING)
    private DoseLogStatus status;

    private LocalDateTime loggedAt;

    private String notes;

    public DoseLog() {
    }

    public DoseLog(
            Dose dose,
            DoseLogStatus status,
            LocalDateTime loggedAt,
            String notes) {

        this.dose = dose;
        this.status = status;
        this.loggedAt = loggedAt;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Dose getDose() {
        return dose;
    }

    public void setDose(Dose dose) {
        this.dose = dose;
    }

    public DoseLogStatus getStatus() {
        return status;
    }

    public void setStatus(DoseLogStatus status) {
        this.status = status;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}