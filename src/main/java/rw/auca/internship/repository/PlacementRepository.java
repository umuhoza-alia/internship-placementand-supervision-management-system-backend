package rw.auca.internship.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.internship.entity.Placement;
public interface PlacementRepository 
        extends JpaRepository<Placement, UUID> {
    
}
