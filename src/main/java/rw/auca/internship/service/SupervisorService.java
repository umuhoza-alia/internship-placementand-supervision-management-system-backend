package rw.auca.internship.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.stereotype.Service;
import rw.auca.internship.entity.Supervisor;
import rw.auca.internship.repository.SupervisorRepository;

@Service
public class SupervisorService {
    
    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }
    // save a new supervisor
    public Supervisor saveSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }
    // Get all supervisors
    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();
    }
    // Get one supervisors by ID
    public Optional<Supervisor> getSupervisorById(UUID id) {
        return supervisorRepository.findById(id);
    }
    //Update a supervisor
    public Optional<Supervisor> updateSupervisor(UUID id, Supervisor updatedSupervisor) {
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

//Delete a supervisor

public boolean deleteSupervisor(UUID id) {
if (supervisorRepository.existsById(id)) {
    supervisorRepository.deleteById(id);
    return true;
}
return false;
}

//find supervisors by internship field name
public List<Supervisor> getAllSupervisorsByInternshipField(String name){
return supervisorRepository.findByInternshipFieldName(name);
}
}
