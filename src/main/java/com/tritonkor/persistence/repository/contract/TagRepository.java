package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.Test;
import com.tritonkor.persistence.entity.filter.TagFilterDto;
import com.tritonkor.persistence.repository.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface TagRepository extends Repository<Tag>, ManyToMany{
    List<Test> findAllTests(UUID tagId);
    Optional<Tag> findByName(String name);
    List<Tag> findAll(int offset, int limit, String sortColumn, boolean ascending,
            TagFilterDto tagFilterDto);
}
