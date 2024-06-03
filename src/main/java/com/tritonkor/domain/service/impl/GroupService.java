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

/**
 * Service class for managing groups.
 */
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

    /**
     * Finds a group by its ID.
     *
     * @param id The ID of the group.
     * @return The group found.
     * @throws EntityNotFoundException if the group with the given ID is not found.
     */
    public Group findById(UUID id) {
        return groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Групу з таким айді не знайдено"));
    }

    /**
     * Finds a group by its name.
     *
     * @param name The name of the group.
     * @return The group found.
     * @throws EntityNotFoundException if the group with the given name is not found.
     */
    public Group findByName(String name) {
        return groupRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Групу з такою назвою не знайдено"));
    }

    /**
     * Finds a group by its code.
     *
     * @param code The code of the group.
     * @return The group found.
     * @throws EntityNotFoundException if the group with the given code is not found.
     */
    public Group findByCode(String code) {
        return groupRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Групу з таким кодом не знайдено"));
    }

    /**
     * Creates a new group.
     *
     * @param request The request containing group information.
     * @return The response containing the created group.
     */
    public GroupResponse createGroup(@Valid CreateGroupRequest request) {
        if (groupRepository.existsByNameOrCode(request.getName(), request.getCode())) {
            return null;
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

    /**
     * Deletes a group.
     *
     * @param request The request containing group ID and user ID.
     * @return true if the group is successfully deleted, false otherwise.
     */
    public boolean deleteGroup(@Valid DeleteGroupRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Групу з таким айді не знайдено"));
        if (Objects.nonNull(group) && group.getTeacherId().equals(request.getUserId())) {
            groupRepository.removeGroup(group.getId());
        }
        return true;
    }

    /**
     * Adds a user to a group.
     *
     * @param request The request containing group code and user ID.
     * @return The response containing the updated group.
     */
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

    /**
     * Removes a user from a group.
     *
     * @param request The request containing group code and user ID.
     * @return true if the user is successfully removed from the group, false otherwise.
     */
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
        try {
            Group group = groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Групу з таким айді не знайдено"));

            return new GroupResponse(group);
        } catch (EntityNotFoundException e) {
            return null;
        }
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

    /**
     * Runs a test for a group.
     *
     * @param request The request containing group and user IDs.
     * @return true if the test is successfully run for the group, false otherwise.
     * @throws EntityNotFoundException if the group with the given ID is not found.
     */
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
