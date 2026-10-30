package rw.auca.internship.repository;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.internship.entity.Intern;
import rw.auca.internship.entity.Placement;

import rw.auca.internship.entity.PlacementStatus;


public interface PlacementRepository 
        extends JpaRepository<Placement, UUID> {
    
List<Placement> findByStatusOrderByStartDateAsc(PlacementStatus status);

List<Placement> findByStartDateBetweenOrderByStartDateAsc(LocalDate startDate, LocalDate endDate);

//C4: find distinct interns assigned to a supervisor
@Query("SELECT DISTINCT p.intern FROM Placement p " +
"WHERE p.supervisor.id = :supervisorId")
List<Intern> findDistinctInternsBySupervisorId(@Param("supervisorId") UUID supervisorId);
}
