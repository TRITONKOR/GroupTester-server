package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.entity.filter.ResultFilterDto;
import com.tritonkor.persistence.repository.Repository;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ResultRepository extends Repository<Result> {
    List<Result> findAllByTestId(UUID testId);

    List<Result> findAllByOwnerId(UUID studentId);

    List<Result> findAll(int offset, int limit, String sortColumn, boolean ascending,
            ResultFilterDto resultFilterDto);
}
