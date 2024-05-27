package com.tritonkor.persistence.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Group extends Entity{
    private String name;
    private String code;
    private UUID teacherId;
    private Map<User, Boolean> users;

    public Group(UUID id, String name, String code, UUID teacherId) {
        super(id);
        this.name = name;
        this.code = code;
        this.teacherId = teacherId;
        this.users = new HashMap<>();
    }

    public Group() {
        super(null);
    }

    public static GroupBuilderId builder() {
        return id -> name -> code -> teacherId -> () -> new Group(id, name, code, teacherId);
    }

    public interface GroupBuilderId {
        GroupBuilderName id(UUID id);
    }

    public interface GroupBuilderName {
        GroupBuilderCode name(String name);
    }

    public interface GroupBuilderCode {
        GroupBuilderTeacherId code(String code);
    }

    public interface GroupBuilderTeacherId {
        GroupBuilder teacherId(UUID teacherId);
    }

    public interface GroupBuilder {
        Group build();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public Map<User, Boolean> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.put(user, false);
    }

    public void removeUser(User user) {
        if(users.containsKey(user)) {
            users.remove(user);
        }
        else {
            System.out.println("The user is not in the group");
        }
    }
}
