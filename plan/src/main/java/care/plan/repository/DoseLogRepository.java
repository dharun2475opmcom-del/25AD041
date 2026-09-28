package care.plan.repository;

import care.plan.model.DoseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoseLogRepository extends JpaRepository<DoseLog, Long> {

    boolean existsByDoseId(Long doseId);
}