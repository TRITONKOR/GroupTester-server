package com.tritonkor.persistence.repository.mapper.impl;

import com.tritonkor.persistence.entity.Test;
import com.tritonkor.persistence.entity.proxy.contract.Questions;
import com.tritonkor.persistence.entity.proxy.contract.Tags;
import com.tritonkor.persistence.entity.proxy.contract.UserProxy;
import com.tritonkor.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The {@code TestRowMapper} class is responsible for mapping rows from a ResultSet to Test objects.
 * It utilizes a UserProxy, Questions proxy, and Tags proxy to lazily fetch associated users, questions, and tags respectively.
 */
@Component
public class TestRowMapper implements RowMapper<Test> {

    private final UserProxy userProxy;
    private final Questions questions;
    private final Tags tags;

    /**
     * Constructs a {@code TestRowMapper} with the given user proxy, questions proxy, and tags proxy.
     *
     * @param userProxy the user proxy to use for lazy fetching of associated users
     * @param questions the questions proxy to use for lazy fetching of associated questions
     * @param tags the tags proxy to use for lazy fetching of associated tags
     */
    public TestRowMapper(UserProxy userProxy, Questions questions, Tags tags) {
        this.userProxy = userProxy;
        this.questions = questions;
        this.tags = tags;
    }

    /**
     * Maps a row from the ResultSet to a Test object.
     *
     * @param rs the ResultSet containing the data to be mapped
     * @return the mapped Test object
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    public Test mapRow(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        UUID ownerId = UUID.fromString(rs.getString("owner_id"));

        return Test.builder()
                .id(id)
                .title(rs.getString("title"))
                .ownerId(ownerId)
                .owner(userProxy)
                .time(rs.getInt("time"))
                .questions(questions)
                .tags(tags)
                .createdAt(rs.getTimestamp("create_date").toLocalDateTime()).build();
    }
}
