package com.zxyono.recite.repository;

import com.zxyono.recite.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    @Query(nativeQuery = true, value = "select * from xy_word where type=?1 and en_content like ?2 order by rand() limit ?3")
    public List<Word> queryAllByFirstLetter(Integer type, String letter, Integer num);

    @Query(nativeQuery = true, value = "select * from xy_word where type=?1 and en_content regexp ?2 order by rand() limit ?3")
    public List<Word> queryAllByLettersLimit(Integer type, String lettersRegex, Integer num);

    @Query(nativeQuery = true, value = "select * from xy_word where type=?1 and en_content regexp ?2 order by rand()")
    public List<Word> queryAllByLetters(Integer type, String lettersRegex);

    public Word queryByEnContent(String enContent);

}
