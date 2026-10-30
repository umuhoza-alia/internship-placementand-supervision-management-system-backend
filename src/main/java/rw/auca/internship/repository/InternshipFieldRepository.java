package rw.auca.internship.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.auca.internship.entity.InternshipField;


public interface InternshipFieldRepository
 extends JpaRepository<InternshipField, UUID> {
    
    //C3: find internship fields that no supervisior holds
    @Query("SELECT f FROM InternshipField f " +
    "WHERE f.supervisors IS EMPTY " )
    List<InternshipField> findUnusedInternshipFields();
    
}