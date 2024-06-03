package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.Test;
import com.tritonkor.persistence.entity.filter.TestFilterDto;
import com.tritonkor.persistence.repository.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TestRepository extends Repository<Test>, ManyToMany {

    List<Tag> findAllTags(UUID testId);
    List<Test> findAllByUserId(UUID userId);

    List<Test> findAll(int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            TestFilterDto testFilterDto);

    List<Test> findAllByUserId(UUID userId,
            int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            TestFilterDto testFilterDto);
}
