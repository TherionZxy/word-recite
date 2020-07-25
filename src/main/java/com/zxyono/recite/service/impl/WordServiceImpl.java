package com.zxyono.recite.service.impl;

import com.zxyono.recite.entity.Word;
import com.zxyono.recite.entity.vo.WordVo;
import com.zxyono.recite.entity.wrapper.BaiduResult;
import com.zxyono.recite.entity.wrapper.WordWrapper;
import com.zxyono.recite.enums.ExceptionEnum;
import com.zxyono.recite.exception.WordException;
import com.zxyono.recite.repository.WordRepository;
import com.zxyono.recite.service.WordService;
import com.zxyono.recite.utils.BaiduTranslation;
import com.zxyono.recite.utils.ListUtils;
import com.zxyono.recite.utils.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Resource
    private WordRepository wordRepository;

    @Override
    public WordWrapper findOne(String word) {
        WordWrapper wordWrapper = null;
        Word result = wordRepository.queryByEnContent(word);
        System.out.println(result);
        if (result != null) {
            wordWrapper = new WordWrapper();
            wordWrapper.setEnContent(result.getEnContent());
            wordWrapper.setZhContent(result.getZhContent());
        }
        return wordWrapper;
    }

    @Override
    public WordWrapper findOneRemote(String word) {
        WordWrapper wordWrapper = null;
        BaiduResult result = BaiduTranslation.remoteTranslateByBaidu(word);
        if (result != null) {
            wordWrapper = new WordWrapper();
            wordWrapper.setEnContent(result.getTransResult().get(0).get("src"));
            wordWrapper.setZhContent(result.getTransResult().get(0).get("dst"));
        }
        return wordWrapper;
    }

    @Override
    public int addWords(WordVo wordVo) {
        if (!wordVo.checkWordVo()) {
            throw new WordException(ExceptionEnum.WORD_PARAM_EXCEPTION);
        }

        List<Word> words = new ArrayList<>();

        String[] en = wordVo.getEn();
        String[] zh = wordVo.getZh();
        for(int i=0; i<en.length; i++) {
            Word word = new Word();
            word.setEnContent(en[i]);
            word.setZhContent(zh[i]);

            // 查询是否有与数据库重复的单词，若有则为了防止重复阻止重复单词提交
            if (wordRepository.queryByEnContent(en[i]) == null) {
                words.add(word);
            }
        }

        return wordRepository.saveAll(words).size();
    }

    @Override
    public List<Word> findWordsByLetters(String letters, Integer num) {
        if (!StringUtils.checkString(letters, null, "char")) {
            throw new WordException(ExceptionEnum.WORD_PARAM_EXCEPTION);
        }
        return wordRepository.queryAllByFirstLetter(letters+"%", num);
    }

    @Override
    public List<Word> findWordsByParams(String letters, Integer num) {
        if (letters.length() < 0 || !StringUtils.checkString(letters, null, "char")) {
            throw new WordException(ExceptionEnum.WORD_PARAM_EXCEPTION);
        }

        String[] letterArr = StringUtils.delRepeat(letters).split("");
        Specification<Word> specification = new Specification<Word>() {
            @Override
            public Predicate toPredicate(Root<Word> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                for (String letter: letterArr) {
                    predicates.add(criteriaBuilder.like(root.get("enContent"), letter + "%"));
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = Sort.by(Sort.Direction.ASC, "enContent");
        List<Word> words = wordRepository.findAll(specification, PageRequest.of(0, num, sort)).getContent();

        return ListUtils.shuffle(words);
    }
}