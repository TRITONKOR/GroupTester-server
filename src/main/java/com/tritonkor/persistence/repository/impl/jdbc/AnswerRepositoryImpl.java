package com.tritonkor.persistence.repository.impl.jdbc;

import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.entity.filter.AnswerFilterDto;
import com.tritonkor.persistence.repository.GenericJdbcRepository;
import com.tritonkor.persistence.repository.contract.AnswerRepository;
import com.tritonkor.persistence.repository.contract.TableNames;
import com.tritonkor.persistence.repository.mapper.impl.AnswerRowMapper;
import com.tritonkor.persistence.util.ConnectionManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl extends GenericJdbcRepository<Answer> implements
        AnswerRepository {

    public AnswerRepositoryImpl(ConnectionManager connectionManager,
            AnswerRowMapper rowMapper) {
        super(connectionManager, rowMapper, TableNames.ANSWERS.getName());
    }

    @Override
    protected Map<String, Object> tableValues(Answer answer) {
        Map<String, Object> values = new LinkedHashMap<>();

        if (Objects.nonNull(answer.getQuestionId())) {
            values.put("question_id", answer.getQuestionId());
        }
        if (!answer.getText().isBlank()) {
            values.put("text", answer.getText());
        }
        if (Objects.nonNull(answer.getCorrect())) {
            values.put("is_correct", answer.getCorrect());
        }

        return values;
    }

    @Override
    public List<Answer> findAllByQuestionId(UUID questionId) {
        return findAllWhere(STR."question_id = '\{questionId}'");
    }

    @Override
    public Optional<Answer> findByText(String text) {
        return findBy("text", text);
    }

    @Override
    public List<Answer> findAll(
            int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            AnswerFilterDto answerFilterDto) {

        HashMap<String, Object> filters = new HashMap<>();

        if (!answerFilterDto.text().isBlank()) {
            filters.put("text", answerFilterDto.text());
        }
        if (Objects.nonNull(answerFilterDto.questionId())) {
            filters.put("question_id", answerFilterDto.questionId());
        }

        return findAll(offset, limit, sortColumn, ascending, filters);
    }
}
