package rw.auca.internship.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import rw.auca.internship.entity.Supervisor;

public interface SupervisorRepository
 extends JpaRepository<Supervisor, UUID> {
    
@Query ("SELECT s FROM Supervisor s JOIN s.internshipFields f" +
" WHERE LOWER(f.name) = LOWER(:name)")
List<Supervisor> findByInternshipFieldName(@Param("name") String name);


}
