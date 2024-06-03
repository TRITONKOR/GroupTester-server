package com.tritonkor.net.controller;

import com.tritonkor.domain.service.impl.GroupService;
import com.tritonkor.net.request.group.ChooseTestRequest;
import com.tritonkor.net.request.group.CreateGroupRequest;
import com.tritonkor.net.request.group.DeleteGroupRequest;
import com.tritonkor.net.request.group.JoinGroupRequest;
import com.tritonkor.net.request.group.LeaveGroupRequest;
import com.tritonkor.net.request.group.ChangeUserStatusRequest;
import com.tritonkor.net.request.group.RunTestRequest;
import com.tritonkor.net.response.GroupResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code GroupController} class handles group-related HTTP requests.
 */
@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * Creates a new group.
     *
     * @param request the request to create a group
     * @return a group response
     */
    @PostMapping("/new")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request) {
        GroupResponse response = groupService.createGroup(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a group.
     *
     * @param request the request to delete a group
     * @return a boolean indicating whether the operation was successful
     */
    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteGroup(@Valid @RequestBody DeleteGroupRequest request) {
        return ResponseEntity.ok(groupService.deleteGroup(request));
    }

    /**
     * Joins a user to a group.
     *
     * @param request the request to join a group
     * @return a group response
     */
    @PostMapping("/join")
    public ResponseEntity<GroupResponse> joinGroup(@Valid @RequestBody JoinGroupRequest request) {
        return ResponseEntity.ok(groupService.addUserToGroup(request));
    }

    /**
     * Leaves a group.
     *
     * @param request the request to leave a group
     * @return a boolean indicating whether the operation was successful
     */
    @PostMapping("/leave")
    public ResponseEntity<Boolean> leaveGroup(@Valid @RequestBody LeaveGroupRequest request) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(request));
    }

    /**
     * Chooses a test for a group.
     *
     * @param request the request to choose a test
     * @return a boolean indicating whether the operation was successful
     */
    @PostMapping("/test")
    public ResponseEntity<Boolean> chooseTest(@Valid @RequestBody ChooseTestRequest request) {
        return ResponseEntity.ok(groupService.chooseTest(request));
    }

    /**
     * Retrieves the status of a group.
     *
     * @param groupId the ID of the group
     * @return a group response
     */
    @GetMapping("/status")
    public ResponseEntity<GroupResponse> getGroupStatus(@RequestParam("groupId") UUID groupId) {
        return ResponseEntity.ok(groupService.getGroupStatus(groupId));
    }

    /**
     * Runs a test for a group.
     *
     * @param request the request to run a test
     * @return a boolean indicating whether the operation was successful
     */
    @PostMapping("/run-test")
    public ResponseEntity<Boolean> runtTest(@Valid @RequestBody RunTestRequest request) {
        return ResponseEntity.ok(groupService.runTestForGroup(request));
    }

    /**
     * Changes the status of a user in a group.
     *
     * @param request the request to change user status
     * @return a boolean indicating whether the operation was successful
     */
    @PostMapping("/user/change-status")
    public ResponseEntity<Boolean> userChangeStatus(@Valid @RequestBody ChangeUserStatusRequest request) {
        return ResponseEntity.ok(groupService.changeUserStatus(request));
    }
}


