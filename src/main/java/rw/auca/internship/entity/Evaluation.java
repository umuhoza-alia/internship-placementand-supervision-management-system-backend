package rw.auca.internship.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String criterion;

    private String comments;

    private int score;

    private LocalDate evaluatedDate;

    @ManyToOne
    @JoinColumn(name = "placement_id")
    private Placement placement;

    public Evaluation() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getEvaluatedDate() {
        return evaluatedDate;
    }

    public void setEvaluatedDate(LocalDate evaluatedDate) {
        this.evaluatedDate = evaluatedDate;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }
}