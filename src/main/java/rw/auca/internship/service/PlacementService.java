
package rw.auca.internship.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import rw.auca.internship.repository.EvaluationRepository;
import org.springframework.stereotype.Service;

import rw.auca.internship.entity.Placement;
import rw.auca.internship.entity.PlacementStatus;
import rw.auca.internship.entity.Intern;
import rw.auca.internship.entity.Supervisor;

import rw.auca.internship.repository.PlacementRepository;
import rw.auca.internship.repository.SupervisorRepository;

@Service
public class PlacementService {

    private final PlacementRepository placementRepository;
    private final SupervisorRepository supervisorRepository;
    private final EvaluationRepository evaluationRepository;

   public PlacementService(
        PlacementRepository placementRepository,
        SupervisorRepository supervisorRepository,
        EvaluationRepository evaluationRepository) {

    this.placementRepository = placementRepository;
    this.supervisorRepository = supervisorRepository;
    this.evaluationRepository = evaluationRepository;
}

// F1: Get placement counts grouped by status
public List<Object[]> getPlacementCountsByStatus() {
    return placementRepository.countPlacementsByStatus();
}

// F2: Cancel placements for a supervisor on a specific day
public int cancelPlacementsBySupervisorAndDate(
        UUID supervisorId, LocalDate date) {

    return placementRepository.cancelPlacementsBySupervisorAndDate(
            supervisorId, date);
}

    // D1: Save a new placement with validation
public String savePlacement(Placement placement) {

    // Check if the placement date is in the past
    if (placement.getStartDate().isBefore(LocalDate.now())) {
        return "Placement date cannot be in the past";
    }

    // Check if the supervisor already has a placement
    // on the same date that is not DECLINED
    boolean alreadyAssigned =
            placementRepository.existsBySupervisorIdAndStartDateAndStatusNot(
                    placement.getSupervisor().getId(),
                    placement.getStartDate(),
                    PlacementStatus.DECLINED
            );

    if (alreadyAssigned) {
        return "Supervisor already has a placement starting on that date";
    }

    // Set the initial status to APPLIED
    placement.setStatus(PlacementStatus.APPLIED);

    placementRepository.save(placement);

    return "Placement saved successfully";
}

    // Get all placements
    public List<Placement> getAllPlacements() {
        return placementRepository.findAll();
    }

    // Get one placement by ID
    public Optional<Placement> getPlacementById(UUID id) {
        return placementRepository.findById(id);
    }

    // Update an existing placement
    public Optional<Placement> updatePlacement(UUID id, Placement updatedPlacement) {
        return placementRepository.findById(id)
                .map(existingPlacement -> {
                    existingPlacement.setStartDate(updatedPlacement.getStartDate());
                    existingPlacement.setPosition(updatedPlacement.getPosition());
                    existingPlacement.setDurationWeeks(updatedPlacement.getDurationWeeks());
                    existingPlacement.setIntern(updatedPlacement.getIntern());
                    existingPlacement.setSupervisor(updatedPlacement.getSupervisor());

                    return placementRepository.save(existingPlacement);
                });
    }

   // E3: Delete a placement only if it has no evaluations
public String deletePlacement(UUID id) {

    if (!placementRepository.existsById(id)) {
        return "The placement with that id does not exist";
    }

    if (evaluationRepository.existsByPlacementId(id)) {
        return "That placement has evaluations, delete them first";
    }

    placementRepository.deleteById(id);

    return "Placement deleted successfully";
}

    // Find placements by status
    public List<Placement> getPlacementsByStatus(PlacementStatus status) {
        return placementRepository.findByStatusOrderByStartDateAsc(status);
    }

    // Find placements between two dates
    public List<Placement> getPlacementsBetweenDates(
            LocalDate startDate, LocalDate endDate) {

        return placementRepository
                .findByStartDateBetweenOrderByStartDateAsc(startDate, endDate);
    }

    // Find a supervisor by ID
    public Optional<Supervisor> getSupervisorById(UUID supervisorId) {
        return supervisorRepository.findById(supervisorId);
    }

    // Find interns assigned to a supervisor
    public List<Intern> getInternsBySupervisor(UUID supervisorId) {
        return placementRepository.findDistinctInternsBySupervisorId(supervisorId);
    }

    // D2: Update placement status
public String updatePlacementStatus(UUID id, PlacementStatus newStatus) {

    // Find the placement
    Optional<Placement> optionalPlacement =
            placementRepository.findById(id);

    // Check if placement exists
    if (optionalPlacement.isEmpty()) {
        return "The placement with that id does not exist";
    }

    Placement placement = optionalPlacement.get();

    PlacementStatus currentStatus = placement.getStatus();

    // Check whether the status transition is allowed
    boolean allowed = false;

    if (currentStatus == PlacementStatus.APPLIED &&
            (newStatus == PlacementStatus.APPROVED ||
             newStatus == PlacementStatus.DECLINED)) {

        allowed = true;

    } else if (currentStatus == PlacementStatus.APPROVED &&
            (newStatus == PlacementStatus.STARTED ||
             newStatus == PlacementStatus.DECLINED)) {

        allowed = true;
    }

    // Reject an invalid status change
    if (!allowed) {
        return "Cannot change status from " +
                currentStatus + " to " + newStatus;
    }

    // Save the new status
    placement.setStatus(newStatus);
    placementRepository.save(placement);

    return "Placement status updated successfully";
}
}
