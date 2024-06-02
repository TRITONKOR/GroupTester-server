package com.tritonkor.persistence.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Group extends Entity{
    private String name;
    private String code;
    private Boolean readyToTesting;
    private Boolean canApplyNewUsers;
    private UUID teacherId;
    private Test test;
    private Map<User, Boolean> users;
    private Map<User, Mark> results;

    public Group(UUID id, String name, String code, UUID teacherId, Test test) {
        super(id);
        this.name = name;
        this.code = code;
        this.readyToTesting = false;
        this.canApplyNewUsers = true;
        this.teacherId = teacherId;
        this.test = test;
        this.users = new HashMap<>();
        this.results = new HashMap<>();
    }

    public Group() {
        super(null);
    }

    public static GroupBuilderId builder() {
        return id -> name -> code -> teacherId -> test -> () -> new Group(id, name, code, teacherId, test);
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
        GroupBuilderTest teacherId(UUID teacherId);
    }

    public interface GroupBuilderTest {
        GroupBuilder test(Test test);
    }

    public interface GroupBuilder {
        Group build();
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
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

    public Boolean getReadyToTesting() {
        return readyToTesting;
    }

    public void setReadyToTesting(Boolean readyToTesting) {
        this.readyToTesting = readyToTesting;
    }

    public Boolean getCanApplyNewUsers() {
        return canApplyNewUsers;
    }

    public void setCanApplyNewUsers(Boolean canApplyNewUsers) {
        this.canApplyNewUsers = canApplyNewUsers;
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

    public Map<User, Mark> getResults() {
        return results;
    }

    public void addResult(User user, Mark mark) {
        results.put(user, mark);
    }
}
