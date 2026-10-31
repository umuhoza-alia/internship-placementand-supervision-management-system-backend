package rw.auca.internship.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.internship.entity.Placement;
import rw.auca.internship.service.PlacementService;

import java.time.LocalDate;
import rw.auca.internship.entity.PlacementStatus;

@RestController 
@RequestMapping("/api/placements")
public class PlacementController {

    private final PlacementService placementService;

    public PlacementController(PlacementService placementService) {
        this.placementService = placementService;

    }

   // D1: Save placement with validation
@PostMapping("/save")
public ResponseEntity<String> savePlacement(
        @RequestBody Placement placement) {

    String result = placementService.savePlacement(placement);

    if (result.equals("Placement date cannot be in the past")) {
        return ResponseEntity.badRequest().body(result);

    } else if (result.equals(
            "Supervisor already has a placement starting on that date")) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);

    } else {
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

//Get all placements
@GetMapping("/all")
public ResponseEntity<List<Placement>> getAllPlacements() {

    return ResponseEntity.ok(
        placementService.getAllPlacements());
    
}

//Get one placement by ID
@GetMapping("/{id}")
public ResponseEntity<Placement> getPlacementById(
    @PathVariable UUID id) {

        return placementService.getPlacementById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    //update a placement
    @PutMapping ("/update/{id}")
    public ResponseEntity<?> updatePlacement(
        @PathVariable UUID id,
        @RequestBody Placement placement) {

            return placementService.updatePlacement(id, placement)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());

        }
//Delete a placement
@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deletePlacement(@PathVariable UUID id) {

    String result = placementService.deletePlacement(id);

    if (result.equals("The placement with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    } else if (result.equals("That placement has evaluations, delete them first")) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    } else {
        return ResponseEntity.ok(result);
    }
}

//B2: Get placements by status
@GetMapping("/by-status")
public ResponseEntity<List<Placement>> getPlacementsByStatus(
     @RequestParam PlacementStatus status){

        return ResponseEntity.ok(
            placementService.getPlacementsByStatus(status));
        
     }
//B3:v Get placements between two dates
@GetMapping("/between")
public ResponseEntity<List<Placement>> getPlacementsBetweenDates(
    @RequestParam String start,
    @RequestParam  String end){
LocalDate startDate = LocalDate.parse(start);
    LocalDate endDate = LocalDate.parse(end);

    return ResponseEntity.ok(
        placementService.getPlacementsBetweenDates(startDate, endDate)
    );

    }

    // D2: Change placement status
@PatchMapping("/{id}/status")
public ResponseEntity<String> updatePlacementStatus(
        @PathVariable UUID id,
        @RequestParam PlacementStatus status) {

    String result = placementService.updatePlacementStatus(id, status);

    if (result.equals("The placement with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

    } else if (result.startsWith("Cannot change status from")) {
        return ResponseEntity.badRequest().body(result);

    } else {
        return ResponseEntity.ok(result);
    }
}

}





