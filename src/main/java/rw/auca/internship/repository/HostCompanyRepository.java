package rw.auca.internship.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.internship.entity.HostCompany;

public interface HostCompanyRepository 
extends JpaRepository<HostCompany, UUID> {
    
}
