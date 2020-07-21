package com.zxyono.recite.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "xy_scores")
@Data
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scores_id")
    private Long scoresId;

    @Column(name = "correct")
    private Integer correct;

    @Column(name = "wrong")
    private Integer wrong;

    @Transient
    @JsonProperty(value = "value")
    private Double calValue() {
        return correct / (wrong + correct + 0.0);
    }

    @ManyToMany
    @JoinTable(name = "xy_scores_answer"
            , joinColumns = {@JoinColumn(name = "scores_id")}
            , inverseJoinColumns = {@JoinColumn(name = "answer_id")}
            , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
            , inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Answer> answers;

}