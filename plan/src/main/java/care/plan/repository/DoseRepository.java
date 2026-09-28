package care.plan.repository;

import care.plan.enums.DoseStatus;
import care.plan.model.Dose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DoseRepository extends JpaRepository<Dose, Long> {

    List<Dose> findByScheduleId(Long scheduleId);

    List<Dose> findByScheduledDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    List<Dose> findByStatus(DoseStatus status);

    boolean existsByScheduleIdAndScheduledDateTime(
            Long scheduleId,
            LocalDateTime scheduledDateTime);
}