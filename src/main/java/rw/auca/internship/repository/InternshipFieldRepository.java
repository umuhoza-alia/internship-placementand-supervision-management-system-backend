package rw.auca.internship.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.internship.entity.InternshipField;


public interface InternshipFieldRepository
 extends JpaRepository<InternshipField, UUID> {
    
}