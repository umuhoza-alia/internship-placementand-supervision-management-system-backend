package rw.auca.internship.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.internship.entity.Evaluation;
import rw.auca.internship.entity.Placement;
import rw.auca.internship.entity.PlacementStatus;
import rw.auca.internship.repository.EvaluationRepository;
import rw.auca.internship.repository.PlacementRepository;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final PlacementRepository placementRepository;

    public EvaluationService(
            EvaluationRepository evaluationRepository,
            PlacementRepository placementRepository) {
        this.evaluationRepository = evaluationRepository;
        this.placementRepository = placementRepository;
    }

    // E1: Save an evaluation
    public String saveEvaluation(Evaluation evaluation, UUID placementId) {

        Optional<Placement> optionalPlacement =
                placementRepository.findById(placementId);

        if (optionalPlacement.isEmpty()) {
            return "The placement with that id does not exist";
        }

        Placement placement = optionalPlacement.get();

        if (placement.getStatus() != PlacementStatus.STARTED) {
            return "Evaluations are only for STARTED placements";
        }

        evaluation.setPlacement(placement);
        evaluation.setEvaluatedDate(LocalDate.now());

        evaluationRepository.save(evaluation);

        return "Evaluation saved successfully";
    }

    // E2: Get evaluations belonging to an intern
    public List<Evaluation> getEvaluationsByIntern(String regNumber) {
        return evaluationRepository.findEvaluationsByInternRegNumber(regNumber);
    }

    // E3: Delete an evaluation
    public String deleteEvaluation(UUID id) {

        Optional<Evaluation> optionalEvaluation =
                evaluationRepository.findById(id);

        if (optionalEvaluation.isEmpty()) {
            return "The evaluation with that id does not exist";
        }

        evaluationRepository.deleteById(id);

        return "Evaluation deleted successfully";
    }

    // E3: Check whether a placement has evaluations
    public boolean hasEvaluations(UUID placementId) {
        return evaluationRepository.existsByPlacementId(placementId);
    }
}