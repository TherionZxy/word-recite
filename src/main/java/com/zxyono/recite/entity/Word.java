package com.zxyono.recite.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "xy_word")
@Data
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordId;

    @Column(name = "en_content", length = 512)
    private String enContent;

    @Column(name = "zh_content", length = 1024)
    private String zhContent;

}