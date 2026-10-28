package rw.auca.internship.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.internship.entity.Intern;

public interface InternRepository 
        extends JpaRepository<Intern, UUID> {
    List<Intern> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

        }