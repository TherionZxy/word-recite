package com.zxyono.recite.repository;

import com.zxyono.recite.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    @Query(nativeQuery = true, value = "select * from xy_word where en_content like ?1 order by rand() limit ?2")
    public List<Word> queryAllByFirstLetter(String letter, Integer num);

    public Word queryByEnContent(String enContent);

}
