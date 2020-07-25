package com.zxyono.recite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "xy_answer")
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "en_answer")
    private String enAnswer;

    @Column(name = "zh_answer")
    private String zhAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private Word word;

//    @ManyToOne
//    private Scores scores;
}