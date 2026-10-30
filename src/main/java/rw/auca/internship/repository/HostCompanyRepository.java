package rw.auca.internship.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.internship.entity.HostCompany;

public interface HostCompanyRepository 
extends JpaRepository<HostCompany, UUID> {
//find a host company by company number
Optional<HostCompany> findByCompanyNumber(int companyNumber);

    // find host companies without supervisors(C2)
    @Query("SELECT c FROM HostCompany c " +
    "WHERE NOT EXISTS " +
    "(SELECT s FROM Supervisor s WHERE s.hostCompany = c) " +
           "ORDER BY c.companyNumber ASC")
     List<HostCompany> findFreeHostCompanies();
}
