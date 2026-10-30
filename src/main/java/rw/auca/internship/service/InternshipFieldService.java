package rw.auca.internship.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.stereotype.Service;
import rw.auca.internship.entity.InternshipField;
import rw.auca.internship.repository.InternshipFieldRepository;

@Service
public class InternshipFieldService {
    
    private final InternshipFieldRepository internshipFieldRepository;

    public InternshipFieldService(InternshipFieldRepository internshipFieldRepository) {
        this.internshipFieldRepository = internshipFieldRepository;
    }
    
    public InternshipField saveInternshipField(InternshipField internshipField) {
        return internshipFieldRepository.save(internshipField);
    }
    public List<InternshipField> getAllInternshipFields() {
        return internshipFieldRepository.findAll();
    }
    public Optional<InternshipField> getInternshipFieldById(UUID id) {
        return internshipFieldRepository.findById(id);
    }
    public Optional<InternshipField> updateInternshipField(UUID id, InternshipField updatedInternshipField) {
        return internshipFieldRepository.findById(id)
                .map(existingField -> {
                    existingField.setName(updatedInternshipField.getName());
                    return internshipFieldRepository.save(existingField);
                });
    }

    public boolean deleteInternshipField(UUID id) {
        if (internshipFieldRepository.existsById(id)) {
            internshipFieldRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<InternshipField> getUnusedInternshipFields() {
        return internshipFieldRepository.findUnusedInternshipFields();
    }
}
