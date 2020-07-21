package com.zxyono.recite.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "xy_answer")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "en_answer")
    private String enAnswer;

    @Column(name = "zh_answer")
    private String zhAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}