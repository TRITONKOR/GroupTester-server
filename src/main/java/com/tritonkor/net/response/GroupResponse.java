package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Group;
import com.tritonkor.persistence.entity.Mark;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponse {
    private String id;
    private String name;
    private String code;
    private Boolean readyToTesting;
    private Boolean canApplyNewUsers;
    private TestResponse test;
    private Map<UserResponse, Boolean> users;
    private Map<UserResponse, Optional<Mark>> results;

    public GroupResponse(Group group) {
        this.id = group.getId().toString();
        this.name = group.getName();
        this.code = group.getCode();
        this.readyToTesting = group.getReadyToTesting();
        this.canApplyNewUsers = group.getCanApplyNewUsers();
        this.users = group.getUsers().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new UserResponse(entry.getKey()),
                        Map.Entry::getValue
                ));
        this.results = new HashMap<>();
        group.getResults().forEach((key, value) -> this.results.put(new UserResponse(key), Optional.ofNullable(value)));


        if (Objects.nonNull(group.getTest())) {
            this.test = new TestResponse(group.getTest());
        }
        else {
            this.test = null;
        }
    }
}
