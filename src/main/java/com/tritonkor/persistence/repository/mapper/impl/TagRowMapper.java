package com.tritonkor.persistence.repository.mapper.impl;

import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.proxy.contract.Tests;
import com.tritonkor.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The {@code TagRowMapper} class is responsible for mapping rows from a ResultSet to Tag objects.
 * It utilizes a Tests proxy to lazily fetch associated tests.
 */
@Component
public class TagRowMapper implements RowMapper<Tag> {
    private final Tests tests;

    /**
     * Constructs a {@code TagRowMapper} with the given tests proxy.
     *
     * @param tests the tests proxy to use for lazy fetching of associated tests
     */
    public TagRowMapper(Tests tests) {
        this.tests = tests;
    }

    /**
     * Maps a row from the ResultSet to a Tag object.
     *
     * @param rs the ResultSet containing the data to be mapped
     * @return the mapped Tag object
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    public Tag mapRow(ResultSet rs) throws SQLException {
        UUID tagId = UUID.fromString(rs.getString("id"));
        return Tag.builder()
                .id(tagId)
                .name(rs.getString("name"))
                .tests(tests)
                .build();
    }
}
