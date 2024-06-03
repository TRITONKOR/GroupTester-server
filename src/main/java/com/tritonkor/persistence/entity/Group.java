package com.tritonkor.persistence.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The {@code Group} class represents a group entity with its associated properties.
 * This class contains information about the group such as its name, code, the teacher responsible,
 * and the test associated with it. It also maintains the users in the group and their test results.
 */
public class Group extends Entity{
    private String name;
    private String code;
    private Boolean readyToTesting;
    private Boolean canApplyNewUsers;
    private UUID teacherId;
    private Test test;
    private Map<User, Boolean> users;
    private Map<User, Mark> results;

    /**
     * Constructs a {@code Group} instance with the specified details.
     *
     * @param id The unique identifier for this group.
     * @param name The name of the group.
     * @param code The unique code of the group.
     * @param teacherId The unique identifier of the teacher associated with the group.
     * @param test The test associated with the group.
     */
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

    /**
     * Default constructor for {@code Group} which initializes the ID to {@code null}.
     */
    public Group() {
        super(null);
    }

    /**
     * Provides a builder for creating instances of {@code Group}.
     *
     * @return A {@code GroupBuilderId} instance to start building a {@code Group}.
     */
    public static GroupBuilderId builder() {
        return id -> name -> code -> teacherId -> test -> () -> new Group(id, name, code, teacherId, test);
    }

    /**
     * Interface for the {@code Group} builder to set the ID.
     */
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

    /**
     * Adds a user to the group with an initial inactive status.
     *
     * @param user The user to be added to the group.
     */
    public void addUser(User user) {
        users.put(user, false);
    }

    /**
     * Removes a user from the group.
     *
     * @param user The user to be removed from the group.
     */
    public void removeUser(User user) {
        if(users.containsKey(user)) {
            users.remove(user);
        }
        else {
            System.out.println("The user is not in the group");
        }
    }

    /**
     * Gets the map of users and their test results.
     *
     * @return The map of users and their results.
     */
    public Map<User, Mark> getResults() {
        return results;
    }

    /**
     * Adds a test result for a user in the group.
     *
     * @param user The user whose result is to be added.
     * @param mark The test result to be added.
     */
    public void addResult(User user, Mark mark) {
        results.put(user, mark);
    }
}
