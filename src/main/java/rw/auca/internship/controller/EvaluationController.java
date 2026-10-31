package rw.auca.internship.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.internship.entity.Evaluation;
import rw.auca.internship.service.EvaluationService;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    // E1: Save an evaluation
    @PostMapping("/save")
    public ResponseEntity<String> saveEvaluation(
            @RequestBody Evaluation evaluation,
            @RequestParam UUID placementId) {

        String result = evaluationService.saveEvaluation(evaluation, placementId);

        if (result.equals("The placement with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Evaluations are only for STARTED placements")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    // E2: Get evaluations by intern registration number
    @GetMapping("/intern/{regNumber}")
    public ResponseEntity<List<Evaluation>> getEvaluationsByIntern(
            @PathVariable String regNumber) {

        List<Evaluation> evaluations =
                evaluationService.getEvaluationsByIntern(regNumber);

        return ResponseEntity.ok(evaluations);
    }

    // E3: Delete an evaluation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvaluation(@PathVariable UUID id) {

        String result = evaluationService.deleteEvaluation(id);

        if (result.equals("The evaluation with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}