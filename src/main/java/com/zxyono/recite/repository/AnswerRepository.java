package com.zxyono.recite.repository;

import com.zxyono.recite.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    public Answer queryAnswerByAnswerId(Long answerId);
}
