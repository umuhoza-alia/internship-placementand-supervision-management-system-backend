package rw.auca.internship.controller;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.internship.entity.InternshipField;
import rw.auca.internship.service.InternshipFieldService;

@RestController
@RequestMapping("/api/internship-fields")
public class InternshipFieldController {

    private final InternshipFieldService internshipFieldService;

    public InternshipFieldController(InternshipFieldService internshipFieldService) {
        this.internshipFieldService = internshipFieldService;
    }

    // Save a new internship field
    @PostMapping("/save")
    public ResponseEntity<InternshipField> saveInternshipField(
            @RequestBody InternshipField internshipField) {

        InternshipField savedField =
                internshipFieldService.saveInternshipField(internshipField);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedField);
    }

    // Get all internship fields
    @GetMapping("/all")
    public ResponseEntity<List<InternshipField>> getAllInternshipFields() {

        return ResponseEntity.ok(
                internshipFieldService.getAllInternshipFields());
    }

    // Get one internship field by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInternshipFieldById(@PathVariable UUID id) {

        Optional<InternshipField> field =
                internshipFieldService.getInternshipFieldById(id);

        if (field.isPresent()) {
            return ResponseEntity.ok(field.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The internship field with that id does not exist");
        }
    }

    // Update an internship field
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateInternshipField(
            @PathVariable UUID id,
            @RequestBody InternshipField updatedField) {

        Optional<InternshipField> field =
                internshipFieldService.updateInternshipField(id, updatedField);

        if (field.isPresent()) {
            return ResponseEntity.ok(field.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The internship field with that id does not exist");
        }
    }

    // Delete an internship field
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInternshipField(@PathVariable UUID id) {

        if (internshipFieldService.deleteInternshipField(id)) {
            return ResponseEntity.ok("Internship field deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The internship field with that id does not exist");
        }
    }

    // Get unused internship fields
    @GetMapping("/unused")
    public ResponseEntity<List<InternshipField>> getUnusedInternshipFields() {

        return ResponseEntity.ok(
                internshipFieldService.getUnusedInternshipFields());
    }
}