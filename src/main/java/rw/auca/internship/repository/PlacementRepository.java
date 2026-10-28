package rw.auca.internship.repository;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.internship.entity.Placement;

import rw.auca.internship.entity.PlacementStatus;


public interface PlacementRepository 
        extends JpaRepository<Placement, UUID> {
    
List<Placement> findByStatusOrderByStartDateAsc(PlacementStatus status);

List<Placement> findByStartDateBetweenOrderByStartDateAsc(LocalDate startDate, LocalDate endDate);


}
