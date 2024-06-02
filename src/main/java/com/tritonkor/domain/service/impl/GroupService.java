package com.tritonkor.domain.service.impl;

import com.tritonkor.net.request.group.ChooseTestRequest;
import com.tritonkor.net.request.group.CreateGroupRequest;
import com.tritonkor.net.request.group.DeleteGroupRequest;
import com.tritonkor.net.request.group.JoinGroupRequest;
import com.tritonkor.net.request.group.LeaveGroupRequest;
import com.tritonkor.net.request.group.ChangeUserStatusRequest;
import com.tritonkor.net.request.group.RunTestRequest;
import com.tritonkor.net.response.GroupResponse;
import com.tritonkor.persistence.entity.Group;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.entity.Test;
import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.GroupRepository;
import com.tritonkor.persistence.repository.contract.UserRepository;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TestService testService;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, TestService testService) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.testService = testService;
    }

    public Group findById(UUID id) {
        return groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Групу з таким айді не знайдено"));
    }

    public Group findByName(String name) {
        return groupRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Групу з такою назвою не знайдено"));
    }

    public Group findByCode(String code) {
        return groupRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
    }

    public GroupResponse createGroup(@Valid CreateGroupRequest request) {
        if (groupRepository.existsByNameOrCode(request.getName(), request.getCode())) {
            throw new RuntimeException("Group with the same name or code already exists");
        }

        Group group = Group.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .code(request.getCode())
                .teacherId(request.getUserId())
                .test(null)
                .build();

        groupRepository.addGroup(group);

        return new GroupResponse(group);
    }

    public boolean deleteGroup(@Valid DeleteGroupRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Групу з таким айді не знайдено"));
        if (Objects.nonNull(group) && group.getTeacherId().equals(request.getUserId())) {
            groupRepository.removeGroup(group.getId());
        }
        return true;
    }

    public GroupResponse addUserToGroup(@Valid JoinGroupRequest request) {
        Group group = groupRepository.findByCode(request.getCode()).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
        if (Objects.nonNull(group) && group.getCanApplyNewUsers() && Objects.nonNull(user)) {
            group.addUser(user);

            group.addResult(user, null);
            return new GroupResponse(group);
        }
        return null;
    }

    public boolean removeUserFromGroup(@Valid LeaveGroupRequest request) {
        Group group = groupRepository.findByCode(request.getCode()).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
        if (Objects.nonNull(group) && Objects.nonNull(user)) {
            group.removeUser(user);
        }
        return true;
    }

    public boolean chooseTest(@Valid ChooseTestRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
        if (Objects.nonNull(group) && request.getUserId().equals(group.getTeacherId())) {
            Test test = testService.findById(request.getTestId());
            group.setTest(test);
            return true;
        }
        return false;
    }

    public GroupResponse getGroupStatus(@Valid UUID id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Групу з таким айді не знайдено"));
        if (Objects.nonNull(group)) {
            GroupResponse response = new GroupResponse(group);
            return response;
        }
        return null;
    }

    public boolean changeUserStatus(@Valid ChangeUserStatusRequest request) {
        Group group = groupRepository.findByCode(request.getGroupCode()).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
        Map<User, Boolean> users = group.getUsers();

        if (users.containsKey(user)) {
            users.replace(user, !users.get(user));
            return true;
        }
        return false;
    }

    public boolean runTestForGroup(@Valid RunTestRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
        User user = userRepository.findById(request.getUserID()).orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));

        if (Objects.nonNull(group) && group.getTeacherId().equals(user.getId())) {
            group.setReadyToTesting(true);
            group.setCanApplyNewUsers(false);

            for (Map.Entry<User, Boolean> entry : group.getUsers().entrySet()) {
                entry.setValue(false);
            }

            return true;
        }
        return false;
    }
}
