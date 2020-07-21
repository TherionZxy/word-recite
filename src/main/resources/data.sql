insert into xy_word(word_id, en_content, zh_content)  values(1, 'happy','高兴');
insert into xy_word(word_id, en_content, zh_content)  values(2, 'sad','伤心');
insert into xy_word(word_id, en_content, zh_content)  values(3, 'hungry','肚子饿');


insert into xy_answer(answer_id, en_answer, word_id) values (1, 'heppy', 1);
insert into xy_answer(answer_id, en_answer, word_id) values (2, 'hungry', 3);

insert into xy_scores(scores_id, correct, wrong) values (1, 1, 1);

insert into xy_scores_answer(scores_id, answer_id) values (1, 1);
insert into xy_scores_answer(scores_id, answer_id) values (1, 2);