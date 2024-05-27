package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.entity.filter.UserFilterDto;
import com.tritonkor.persistence.repository.Repository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAll(int offset, int limit, String sortColumn, boolean ascending,
            UserFilterDto userFilterDto);
}
