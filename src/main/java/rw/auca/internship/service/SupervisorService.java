
package rw.auca.internship.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.internship.entity.Supervisor;
import rw.auca.internship.entity.InternshipField;
import rw.auca.internship.entity.HostCompany;

import rw.auca.internship.repository.SupervisorRepository;
import rw.auca.internship.repository.InternshipFieldRepository;
import rw.auca.internship.repository.HostCompanyRepository;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final InternshipFieldRepository internshipFieldRepository;
    private final HostCompanyRepository hostCompanyRepository;

    public SupervisorService(
            SupervisorRepository supervisorRepository,
            InternshipFieldRepository internshipFieldRepository,
            HostCompanyRepository hostCompanyRepository) {

        this.supervisorRepository = supervisorRepository;
        this.internshipFieldRepository = internshipFieldRepository;
        this.hostCompanyRepository = hostCompanyRepository;
    }

    // Save a new supervisor
    public Supervisor saveSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

    // Get all supervisors
    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();
    }

    // Get one supervisor by ID
    public Optional<Supervisor> getSupervisorById(UUID id) {
        return supervisorRepository.findById(id);
    }

    // Update a supervisor
    public Optional<Supervisor> updateSupervisor(
            UUID id, Supervisor updatedSupervisor) {

        return supervisorRepository.findById(id)
                .map(existingSupervisor -> {

                    existingSupervisor.setFirstName(updatedSupervisor.getFirstName());
                    existingSupervisor.setLastName(updatedSupervisor.getLastName());
                    existingSupervisor.setEmail(updatedSupervisor.getEmail());
                    existingSupervisor.setHostCompany(updatedSupervisor.getHostCompany());
                    existingSupervisor.setInternshipFields(updatedSupervisor.getInternshipFields());

                    return supervisorRepository.save(existingSupervisor);
                });
    }

    // Delete a supervisor
    public boolean deleteSupervisor(UUID id) {

        if (supervisorRepository.existsById(id)) {
            supervisorRepository.deleteById(id);
            return true;
        }

        return false;
    }

    // Find supervisors by internship field name (C1)
    public List<Supervisor> getAllSupervisorsByInternshipField(String name) {
        return supervisorRepository.findByInternshipFieldName(name);
    }

    // Find an internship field by ID (D3)
    public Optional<InternshipField> getInternshipFieldById(UUID id) {
        return internshipFieldRepository.findById(id);
    }

    // Find a host company by company number (D4)
    public Optional<HostCompany> getHostCompanyByCompanyNumber(int companyNumber) {
        return hostCompanyRepository.findByCompanyNumber(companyNumber);
    }

    // Check whether another supervisor already uses the host company (D4)
    public boolean isHostCompanyTaken(UUID companyId, UUID supervisorId) {
        return supervisorRepository.existsByHostCompanyIdAndIdNot(
                companyId, supervisorId);
    }
}
