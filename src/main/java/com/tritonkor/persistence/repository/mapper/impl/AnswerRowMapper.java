package com.tritonkor.persistence.repository.mapper.impl;

import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.entity.proxy.contract.QuestionProxy;
import com.tritonkor.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The {@code AnswerRowMapper} class is responsible for mapping rows from a ResultSet to Answer objects.
 * It utilizes a QuestionProxy to lazily fetch associated questions.
 */
@Component
public class AnswerRowMapper implements RowMapper<Answer> {

    private final QuestionProxy questionProxy;

    /**
     * Constructs an {@code AnswerRowMapper} with the given question proxy.
     *
     * @param questionProxy the question proxy to use for lazy fetching of associated questions
     */
    public AnswerRowMapper(QuestionProxy questionProxy) {
        this.questionProxy = questionProxy;
    }

    /**
     * Maps a row from the ResultSet to an Answer object.
     *
     * @param rs the ResultSet containing the data to be mapped
     * @return the mapped Answer object
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    public Answer mapRow(ResultSet rs) throws SQLException {
        UUID answerId = UUID.fromString(rs.getString("id"));
        UUID questionId = UUID.fromString(rs.getString("question_id"));

        return Answer.builder()
                .id(answerId)
                .questionId(questionId)
                .question(questionProxy)
                .text(rs.getString("text"))
                .isCorrect(rs.getBoolean("is_correct"))
                .build();
    }
}
