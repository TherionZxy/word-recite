package com.zxyono.recite.service.impl;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.zxyono.recite.entity.Word;
import com.zxyono.recite.entity.vo.WordVo;
import com.zxyono.recite.entity.wrapper.BaiduResult;
import com.zxyono.recite.entity.wrapper.WordWrapper;
import com.zxyono.recite.enums.ExceptionEnum;
import com.zxyono.recite.exception.BaiduApiException;
import com.zxyono.recite.exception.WordException;
import com.zxyono.recite.repository.WordRepository;
import com.zxyono.recite.service.WordService;
import com.zxyono.recite.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {
    @Resource
    private WordRepository wordRepository;
    @Autowired
    private AipSpeech aipSpeech;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public WordWrapper findOne(String word) {
        WordWrapper wordWrapper = null;
        Word result = wordRepository.queryByEnContent(word);
        if (result != null) {
            wordWrapper = new WordWrapper();
            wordWrapper.setEnContent(result.getEnContent());
            wordWrapper.setZhContent(result.getZhContent());

            BoundHashOperations operations = redisTemplate.boundHashOps("hotwords");
            if (operations.get(result.getEnContent()) != null) {
                operations.put(result.getEnContent(), String.valueOf(Integer.parseInt((String)operations.get(result.getEnContent())) + 1));
            } else {
                operations.put(result.getEnContent(), "1");
            }
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

            BoundHashOperations operations = redisTemplate.boundHashOps("hotwords");
            if (operations.get(wordWrapper.getEnContent()) != null) {
                operations.put(wordWrapper.getEnContent(), String.valueOf(Integer.parseInt((String)operations.get(wordWrapper.getEnContent())) + 1));
            } else {
                operations.put(wordWrapper.getEnContent(), "1");
            }
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
    public List<Word> findWordsByLetters(Integer type, String letters, Integer num) {
        if (!StringUtils.checkString(letters, null, "char")) {
            throw new WordException(ExceptionEnum.WORD_PARAM_EXCEPTION);
        }
        return wordRepository.queryAllByFirstLetter(type, letters+"%", num);
    }

    @Override
    public List<Word> findWordsByParamsRegexLimit(Integer type, String letters, Integer num) {
        if (letters.length() < 0 || !StringUtils.checkString(letters, null, "char")) {
            throw new WordException(ExceptionEnum.WORD_PARAM_EXCEPTION);
        }
        String[] letterArr = StringUtils.delRepeat(letters).split("");
        letterArr[0] = "^" + letterArr[0];

        List<Word> words = null;
        if (num != -1) {
            words = wordRepository.queryAllByLettersLimit(type, String.join("|^", letterArr), num);
        } else {
            words = wordRepository.queryAllByLetters(type, String.join("|^", letterArr));
        }

        return words;
    }

    @Override
    public List<Word> findWordsByParams(Integer type, String letters, Integer num) {
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
                return criteriaBuilder.and(criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])), criteriaBuilder.equal(root.get("type"), type));
            }
        };
        Sort sort = Sort.by(Sort.Direction.ASC, "enContent");
        List<Word> words = wordRepository.findAll(specification, PageRequest.of(0, num, sort)).getContent();

        return ListUtils.shuffle(words);
    }

    @Override
    public List<String> findHotWords(Integer num) {
        BoundHashOperations operations = redisTemplate.boundHashOps("hotwords");
        Map<String, String> map = operations.entries();

        return map.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> -Integer.parseInt(e.getValue())))
                .limit(num)
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    @Override
    public String getAudio(String word, int per) {
        // 如果缓存中存在
        String audioCache = RedisUtils.getAudioCache(redisTemplate, word);
        if (audioCache != null) {
            return audioCache;
        }

        // 如果缓存中不存在
        TtsResponse ttsResponse = BaiduAudio.getAudio(aipSpeech, word, per);
        if (ttsResponse.getResult() != null) {
            throw new BaiduApiException(ExceptionEnum.BAIDU_AUDIO_API_EXCEPTION);
        }
        String contentType = "data:audio/wav;base64,";
        BASE64Encoder encoder = new BASE64Encoder();

        audioCache = contentType + encoder.encode(ttsResponse.getData()).replace("\r\n", "");
        RedisUtils.audioCache(redisTemplate, word, audioCache);

        return audioCache;
    }
}