package com.tritonkor.persistence.repository.impl;

import com.tritonkor.persistence.entity.Group;
import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.repository.contract.GroupRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepositoryImpl implements GroupRepository {
    private Set<Group> groups;

    public GroupRepositoryImpl() {
        this.groups = new HashSet<>();
    }

    @Override
    public Set<Group> findAll() {
        return groups;
    }

    @Override
    public Optional<Group> findById(UUID id) {
        return groups.stream()
                .filter(group -> group.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Group> findByName(String name) {
        return groups.stream()
                .filter(group -> group.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Optional<Group> findByCode(String code) {
        return groups.stream()
                .filter(group -> group.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    @Override
    public Map<User, Boolean> getAllGroupUsers(UUID groupId) {
        return groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst()
                .map(Group::getUsers)
                .orElse(Collections.emptyMap());
    }

    @Override
    public boolean existsByNameOrCode(String name, String code) {
        return groups.stream()
                .anyMatch(group -> group.getName().equals(name) || group.getCode().equals(code));
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(UUID groupId) {
        groups.removeIf(group -> group.getId().equals(groupId));
    }
}
