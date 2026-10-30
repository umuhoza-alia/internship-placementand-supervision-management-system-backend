package rw.auca.internship.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.stereotype.Service;
import rw.auca.internship.entity.Intern;
import rw.auca.internship.repository.InternRepository;

@Service
public class InternService {
    
private final InternRepository internRepository;

public InternService(InternRepository internRepository) {
    this.internRepository = internRepository;
}

// save a new intern
public Intern saveIntern(Intern intern){
    return internRepository.save(intern);
}

//Get all interns
public List<Intern> getAllInterns(){
    return internRepository.findAll();
}

//Get one intern by ID
public Optional<Intern> getInternById(UUID id) {
    return internRepository.findById(id);
}

//update an existing intern
public Optional<Intern> updateIntern(UUID id, Intern updatedIntern){
    return internRepository.findById(id)
    .map(existingIntern -> {
        existingIntern.setFirstName(updatedIntern.getFirstName());
        existingIntern.setLastName(updatedIntern.getLastName());
        existingIntern.setRegNumber(updatedIntern.getRegNumber());
        existingIntern.setEmail(updatedIntern.getEmail());
        existingIntern.setFaculty(updatedIntern.getFaculty());

        return internRepository.save(existingIntern);
    });
}

// Delete an intern
public boolean deleteIntern(UUID id) {
    if (internRepository.existsById(id)){
        internRepository.deleteById(id);
        return true;
    }
    return false;
}

//find interns by last name 
public List<Intern> getInternsByLastName(String lastName) {
    return internRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
}


}
