
package rw.auca.internship.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.internship.entity.HostCompany;
import rw.auca.internship.entity.InternshipField;
import rw.auca.internship.entity.Supervisor;
import rw.auca.internship.service.SupervisorService;

@RestController
@RequestMapping("/api/supervisors")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    // 1. Save a supervisor
    @PostMapping("/save")
    public ResponseEntity<Supervisor> saveSupervisor(
            @RequestBody Supervisor supervisor) {

        Supervisor savedSupervisor =
                supervisorService.saveSupervisor(supervisor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedSupervisor);
    }

    // 2. Get all supervisors
    @GetMapping("/all")
    public ResponseEntity<List<Supervisor>> getAllSupervisors() {

        return ResponseEntity.ok(
                supervisorService.getAllSupervisors());
    }

    // 3. Get a supervisor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupervisorById(
            @PathVariable UUID id) {

        Optional<Supervisor> supervisor =
                supervisorService.getSupervisorById(id);

        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The supervisor with that id does not exist");
        }

        return ResponseEntity.ok(supervisor.get());
    }

    // 4. Update a supervisor
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateSupervisor(
            @PathVariable UUID id,
            @RequestBody Supervisor updatedSupervisor) {

        Optional<Supervisor> supervisor =
                supervisorService.updateSupervisor(id, updatedSupervisor);

        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The supervisor with that id does not exist");
        }

        return ResponseEntity.ok(supervisor.get());
    }

    // 5. Delete a supervisor
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteSupervisor(
            @PathVariable UUID id) {

        boolean deleted = supervisorService.deleteSupervisor(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The supervisor with that id does not exist");
        }

        return ResponseEntity.ok("Supervisor deleted successfully");
    }

    // 6. C1: Find supervisors by internship field
    @GetMapping("/by-internship-field")
    public ResponseEntity<List<Supervisor>> getSupervisorsByInternshipField(
            @RequestParam String name) {

        return ResponseEntity.ok(
                supervisorService.getAllSupervisorsByInternshipField(name));
    }

    // 7. D3: Add an internship field to a supervisor
    @PutMapping("/{supervisorId}/internship-fields/{internshipFieldId}")
    public ResponseEntity<Object> addInternshipField(
            @PathVariable UUID supervisorId,
            @PathVariable UUID internshipFieldId) {

        Optional<Supervisor> supervisor =
                supervisorService.getSupervisorById(supervisorId);

        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The supervisor with that id does not exist");
        }

        Optional<InternshipField> field =
                supervisorService.getInternshipFieldById(internshipFieldId);

        if (field.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("That internship field does not exist");
        }

        Supervisor existingSupervisor = supervisor.get();

        if (existingSupervisor.getInternshipFields() == null) {
            existingSupervisor.setInternshipFields(new ArrayList<>());
        }

        if (existingSupervisor.getInternshipFields().contains(field.get())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("The supervisor already has that internship field");
        }

        existingSupervisor.getInternshipFields().add(field.get());

        supervisorService.saveSupervisor(existingSupervisor);

        return ResponseEntity.ok(
                "Internship field added to the supervisor");
    }

    // 8. D4: Change a supervisor's host company
    @PatchMapping("/{supervisorId}/host-company")
    public ResponseEntity<Object> changeHostCompany(
            @PathVariable UUID supervisorId,
            @RequestParam int companyNumber) {

        // Check whether supervisor exists
        Optional<Supervisor> supervisor =
                supervisorService.getSupervisorById(supervisorId);

        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The supervisor with that id does not exist");
        }

        // Check whether host company exists
        Optional<HostCompany> company =
                supervisorService.getHostCompanyByCompanyNumber(companyNumber);

        if (company.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The host company with that companyNumber does not exist");
        }

        // Check whether another supervisor already uses this company
        if (supervisorService.isHostCompanyTaken(
                company.get().getId(), supervisorId)) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("That host company is taken by another supervisor");
        }

        // Assign the new host company
        supervisor.get().setHostCompany(company.get());

        supervisorService.saveSupervisor(supervisor.get());

        return ResponseEntity.ok("Host company changed successfully");
    }
}
