package com.tritonkor.persistence.repository.impl.jdbc;

import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.entity.filter.ResultFilterDto;
import com.tritonkor.persistence.repository.GenericJdbcRepository;
import com.tritonkor.persistence.repository.contract.ResultRepository;
import com.tritonkor.persistence.repository.contract.TableNames;
import com.tritonkor.persistence.repository.mapper.impl.ResultRowMapper;
import com.tritonkor.persistence.util.ConnectionManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ResultRepositoryImpl extends GenericJdbcRepository<Result> implements
        ResultRepository {

    public ResultRepositoryImpl(ConnectionManager connectionManager,
            ResultRowMapper rowMapper) {
        super(connectionManager, rowMapper, TableNames.RESULTS.getName());
    }

    @Override
    protected Map<String, Object> tableValues(Result result) {
        Map<String, Object> values = new LinkedHashMap<>();

        if (Objects.nonNull(result.getTestId())) {
            values.put("test_id", result.getTestId());
        }
        if (!result.getGroupCode().isBlank()) {
            values.put("group_code", result.getGroupCode());
        }
        if (Objects.nonNull(result.getOwnerId())) {
            values.put("owner_id", result.getOwnerId());
        }
        if (Objects.nonNull(result.getMark())) {
            values.put("mark", result.getMark().getMark());
        }
        values.put("create_date", result.getCreatedAt());
        return values;
    }

    @Override
    public List<Result> findAllByTestId(UUID testId) {
        return findAllWhere(STR."test_id = '\{testId}'");
    }

    @Override
    public List<Result> findAllByOwnerId(UUID studentId) {
        return findAllWhere(STR."owner_id = '\{studentId}'");
    }

    @Override
    public List<Result> findAllByTeacherId(UUID teacherId) {
        final String sql =STR.
                """
                        SELECT r.*
                        FROM results r
                        JOIN tests t ON r.test_id = t.id
                        JOIN users u ON t.owner_id = u.id
                        WHERE u.id = '\{teacherId}';
                        """;

        return executeSql(sql);
    }

    @Override
    public List<Result> findAllByGroupCode(String groupCode) {
        return findAllWhere(STR."group_code = '\{groupCode}'");
    }

    @Override
    public List<Result> findAll(int offset, int limit, String sortColumn, boolean ascending,
            ResultFilterDto resultFilterDto) {
        return findAll(offset, limit, sortColumn, ascending, resultFilterDto, "");
    }

    private List<Result> findAll(int offset, int limit, String sortColumn, boolean ascending,
            ResultFilterDto resultFilterDto, String wherePrefix) {

        StringBuilder where = new StringBuilder(STR."\{wherePrefix} ");
        HashMap<String, Object> filters = new HashMap<>();

        if (Objects.nonNull(resultFilterDto.ownerId())) {
            filters.put("owner_id", resultFilterDto.ownerId());
        }
        if (Objects.nonNull(resultFilterDto.testId())) {
            filters.put("test_id", resultFilterDto.testId());
        }
        if (Objects.nonNull(resultFilterDto.reportId())) {
            filters.put("report_id", resultFilterDto.reportId());
        }


        if (Objects.nonNull(resultFilterDto.createdAtStart())
                && Objects.nonNull(resultFilterDto.createdAtEnd())) {
            where.append(
                    STR."create_date BETWEEN '\{resultFilterDto.createdAtStart()}' AND '\{resultFilterDto.createdAtEnd()}' ");
        } else if (Objects.nonNull(resultFilterDto.createdAtStart())) {
            where.append(STR."create_date >= '\{resultFilterDto.createdAtStart()}' ");
        } else if (Objects.nonNull(resultFilterDto.createdAtEnd())) {
            where.append(STR."create_date <= '\{resultFilterDto.createdAtEnd()}' ");
        }

        return findAll(offset, limit, sortColumn, ascending, filters, where.toString());
    }
}
