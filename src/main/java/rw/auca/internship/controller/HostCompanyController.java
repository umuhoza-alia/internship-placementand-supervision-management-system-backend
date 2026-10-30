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
import org.springframework.web.bind.annotation.RestController;


import rw.auca.internship.entity.HostCompany;
import rw.auca.internship.service.HostCompanyService;

@RestController 
@RequestMapping("/api/host-companies")
public class HostCompanyController {

    private final HostCompanyService hostCompanyService;

    public HostCompanyController(HostCompanyService hostCompanyService) {
        this.hostCompanyService = hostCompanyService;
    }

    // save a new host company

@PostMapping("/save")
public ResponseEntity<HostCompany> saveHostCompany(
            @RequestBody HostCompany hostCompany) {

        HostCompany savedCompany =
                hostCompanyService.saveHostCompany(hostCompany);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }

//get all host companies
@GetMapping("/all")
    public ResponseEntity<List<HostCompany>> getAllHostCompanies() {
        return ResponseEntity.ok(
                hostCompanyService.getAllHostCompanies());
    }

    //Get one host company by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getHostCompanyById(@PathVariable UUID id) {

        Optional<HostCompany> company =
                hostCompanyService.getHostCompanyById(id);

        if (company.isPresent()) {
            return ResponseEntity.ok(company.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The host company with that id does not exist");
        }
    }

//update a host company

@PutMapping("/update/{id}")
    public ResponseEntity<Object> updateHostCompany(
            @PathVariable UUID id,
            @RequestBody HostCompany updatedCompany) {

        Optional<HostCompany> company =
                hostCompanyService.updateHostCompany(id, updatedCompany);

        if (company.isPresent()) {
            return ResponseEntity.ok(company.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The host company with that id does not exist");
        }
    }

    //Delete a host company
@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHostCompany(@PathVariable UUID id) {

        if (hostCompanyService.deleteHostCompany(id)) {
            return ResponseEntity.ok("Host company deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The host company with that id does not exist");
        }
    }

//Get host companies without supervisors
@GetMapping("/free")
public ResponseEntity<List<HostCompany>> getFreeHostCompanies() {
    return ResponseEntity.ok(hostCompanyService.getFreeHostCompanies());
}

}
