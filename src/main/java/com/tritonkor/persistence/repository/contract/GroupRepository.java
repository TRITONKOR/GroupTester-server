package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.Group;
import com.tritonkor.persistence.entity.User;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GroupRepository {
    Set<Group> findAll();
    Optional<Group> findById(UUID id);
    Optional<Group> findByName(String name);
    Optional<Group> findByCode(String code);
    Map<User, Boolean> getAllGroupUsers(UUID groupId);

    boolean existsByNameOrCode(String name, String code);

    void addGroup(Group group);
    void removeGroup(UUID groupId);
}
