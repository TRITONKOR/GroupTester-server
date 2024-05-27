package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.entity.filter.AnswerFilterDto;
import com.tritonkor.persistence.repository.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AnswerRepository extends Repository<Answer> {

    List<Answer> findAllByQuestionId(UUID questionId);

    Optional<Answer> findByText(String text);

    List<Answer> findAll(int offset, int limit, String sortColumn, boolean ascending,
            AnswerFilterDto answerFilterDto);
}
