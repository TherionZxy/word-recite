package com.zxyono.recite.repository;

import com.zxyono.recite.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(value = "select w from Word w where w.enContent like ?1%")
    public List<Word> queryAllByFirstLetter(String letter);



}
