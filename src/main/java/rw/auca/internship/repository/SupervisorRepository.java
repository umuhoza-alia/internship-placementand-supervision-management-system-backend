package rw.auca.internship.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.internship.entity.Supervisor;

public interface SupervisorRepository
 extends JpaRepository<Supervisor, UUID> {
    
}
