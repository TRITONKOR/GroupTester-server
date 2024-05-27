package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Group;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponse {
    private String id;
    private String name;
    private String code;
    private Map<UserResponse, Boolean> users;

    public GroupResponse(Group group) {
        this.id = group.getId().toString();
        this.name = group.getName();
        this.code = group.getCode();
        this.users = group.getUsers().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new UserResponse(entry.getKey()), // Ключ - UserResponse
                        Map.Entry::getValue // Значення - Boolean
                ));
    }
}
