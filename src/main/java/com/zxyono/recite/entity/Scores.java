package com.zxyono.recite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zxyono.recite.utils.DecimalUtils;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "xy_scores")
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scores_id")
    private Long scoresId;

    @Transient
    @JsonProperty(value = "correct")
    private Integer correct;

    @Transient
    @JsonProperty(value = "wrong")
    private Integer wrong;

    @Transient
    @JsonProperty(value = "score")
    private Double score;

    @CreationTimestamp
    @Column(name = "create_time")
    private Date createTime;

    public Integer getCorrect() {
        return answers.stream().mapToInt(item->item.getEnAnswer().trim().equals(item.getWord().getEnContent())?1:0).sum();
    }

    public Integer getWrong() {
        return answers.stream().mapToInt(item->item.getEnAnswer().trim().equals(item.getWord().getEnContent())?0:1).sum();
    }

    public Double getScore() {
        Integer co = correct;
        Integer wr = wrong;
        if (co == null) {
            co = getCorrect();
        }
        if (wr == null) {
            wr = getWrong();
        }
        return DecimalUtils.halfup(co / (co + wr + 0.0) * 100, 2);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "scores_id")
    private List<Answer> answers;

}