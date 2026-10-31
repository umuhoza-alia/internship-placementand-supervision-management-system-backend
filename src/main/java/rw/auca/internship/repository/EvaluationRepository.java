package rw.auca.internship.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.internship.entity.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID> {

    // E2: Find evaluations belonging to an intern
    // Show the newest evaluations first
    @Query("SELECT e FROM Evaluation e " +
           "WHERE e.placement.intern.regNumber = :regNumber " +
           "ORDER BY e.evaluatedDate DESC")
    List<Evaluation> findEvaluationsByInternRegNumber(
            @Param("regNumber") String regNumber);

    // E3: Check whether a placement has evaluations
    boolean existsByPlacementId(UUID placementId);
}