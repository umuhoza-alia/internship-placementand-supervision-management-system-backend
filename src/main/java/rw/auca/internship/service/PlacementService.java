
package rw.auca.internship.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public PlacementService(
            PlacementRepository placementRepository,
            SupervisorRepository supervisorRepository) {

        this.placementRepository = placementRepository;
        this.supervisorRepository = supervisorRepository;
    }

    // Save a new placement
    public Placement savePlacement(Placement placement) {
        return placementRepository.save(placement);
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

    // Delete a placement
    public boolean deletePlacement(UUID id) {
        if (placementRepository.existsById(id)) {
            placementRepository.deleteById(id);
            return true;
        }

        return false;
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
}
