package com.tritonkor.persistence.repository.mapper.impl;

import com.tritonkor.persistence.entity.Mark;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.entity.proxy.contract.TestProxy;
import com.tritonkor.persistence.entity.proxy.contract.UserProxy;
import com.tritonkor.persistence.repository.mapper.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * The {@code ResultRowMapper} class is responsible for mapping rows from a ResultSet to Result objects.
 * It utilizes a UserProxy and a TestProxy to lazily fetch associated users and tests respectively.
 */
@Component
public class ResultRowMapper implements RowMapper<Result> {
    private final UserProxy userProxy;
    private final TestProxy testProxy;

    /**
     * Constructs a {@code ResultRowMapper} with the given user proxy and test proxy.
     *
     * @param userProxy the user proxy to use for lazy fetching of associated users
     * @param testProxy the test proxy to use for lazy fetching of associated tests
     */
    public ResultRowMapper(UserProxy userProxy, TestProxy testProxy) {
        this.userProxy = userProxy;
        this.testProxy = testProxy;
    }

    /**
     * Maps a row from the ResultSet to a Result object.
     *
     * @param rs the ResultSet containing the data to be mapped
     * @return the mapped Result object
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    public Result mapRow(ResultSet rs) throws SQLException {
        UUID resultId = UUID.fromString(rs.getString("id"));
        UUID ownerId = UUID.fromString(rs.getString("owner_id"));
        UUID testId = UUID.fromString(rs.getString("test_id"));

        return Result.builder()
                .id(resultId)
                .ownerId(ownerId)
                .owner(userProxy)
                .testId(testId)
                .test(testProxy)
                .groupCode(rs.getString("group_code"))
                .mark(new Mark(rs.getInt("mark")))
                .createdAt(rs.getTimestamp("create_date").toLocalDateTime())
                .build();
    }
}
