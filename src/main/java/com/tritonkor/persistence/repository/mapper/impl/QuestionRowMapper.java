package com.tritonkor.persistence.repository.mapper.impl;

import com.tritonkor.persistence.entity.Question;
import com.tritonkor.persistence.entity.proxy.contract.Answers;
import com.tritonkor.persistence.entity.proxy.contract.TestProxy;
import com.tritonkor.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The {@code QuestionRowMapper} class is responsible for mapping rows from a ResultSet to Question objects.
 * It utilizes an Answers proxy and a TestProxy to lazily fetch associated answers and tests respectively.
 */
@Component
public class QuestionRowMapper implements RowMapper<Question> {
    private final TestProxy testProxy;
    private final Answers answers;

    /**
     * Constructs a {@code QuestionRowMapper} with the given answers proxy and test proxy.
     *
     * @param answers the answers proxy to use for lazy fetching of associated answers
     * @param testProxy the test proxy to use for lazy fetching of associated tests
     */
    public QuestionRowMapper(Answers answers,TestProxy testProxy) {
        this.answers = answers;
        this.testProxy = testProxy;
    }

    /**
     * Maps a row from the ResultSet to a Question object.
     *
     * @param rs the ResultSet containing the data to be mapped
     * @return the mapped Question object
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    public Question mapRow(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        UUID testId = UUID.fromString(rs.getString("test_id"));

        return Question.builder()
                .id(id)
                .text(rs.getString("text"))
                .image(rs.getBytes("image"))
                .answers(answers)
                .testId(testId)
                .test(testProxy)
                .build();
    }
}
