
package rw.auca.internship.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.internship.entity.Intern;
import rw.auca.internship.entity.Supervisor;
import rw.auca.internship.service.InternService;
import rw.auca.internship.service.PlacementService;

@RestController
@RequestMapping("/api/interns")
public class InternController {

    private final InternService internService;
    private final PlacementService placementService;

    public InternController(
            InternService internService,
            PlacementService placementService) {

        this.internService = internService;
        this.placementService = placementService;
    }

    // Save a new intern
    @PostMapping("/save")
    public ResponseEntity<Intern> saveIntern(@RequestBody Intern intern) {

        Intern savedIntern = internService.saveIntern(intern);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedIntern);
    }

    // Get all interns
    @GetMapping("/all")
    public ResponseEntity<List<Intern>> getAllInterns() {
        return ResponseEntity.ok(internService.getAllInterns());
    }

    // Get one intern by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getInternById(@PathVariable UUID id) {

        Optional<Intern> intern = internService.getInternById(id);

        if (intern.isPresent()) {
            return ResponseEntity.ok(intern.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The intern with that id does not exist");
        }
    }

    // Update an intern
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateIntern(
            @PathVariable UUID id,
            @RequestBody Intern updatedIntern) {

        Optional<Intern> intern =
                internService.updateIntern(id, updatedIntern);

        if (intern.isPresent()) {
            return ResponseEntity.ok(intern.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The intern with that id does not exist");
        }
    }

    // Delete an intern
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteIntern(@PathVariable UUID id) {

        if (internService.deleteIntern(id)) {
            return ResponseEntity.ok("Intern deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The intern with that id does not exist");
        }
    }

    // Find interns by last name (B1)
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Intern>> getInternsByLastName(
            @RequestParam String lastName) {

        return ResponseEntity.ok(
                internService.getInternsByLastName(lastName));
    }

    // Find interns assigned to a supervisor (C4)
    @GetMapping("/of-supervisor/{supervisorId}")
    public ResponseEntity<Object> getInternsOfSupervisor(
            @PathVariable UUID supervisorId) {

        Optional<Supervisor> supervisor =
                placementService.getSupervisorById(supervisorId);

        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The supervisor with that id does not exist");
        }

        return ResponseEntity.ok(
                placementService.getInternsBySupervisor(supervisorId));
    }
}
