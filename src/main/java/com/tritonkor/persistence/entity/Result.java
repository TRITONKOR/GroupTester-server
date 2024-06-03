package com.tritonkor.persistence.entity;

import com.tritonkor.persistence.entity.proxy.contract.TestProxy;
import com.tritonkor.persistence.entity.proxy.contract.UserProxy;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Result} class represents the result of a test for a specific user.
 * This class contains information about the user who took the test, the test itself,
 * the group code, the mark received, and the timestamp when the result was created.
 */
public class Result extends Entity implements Comparable<Result> {
    private final UUID ownerId;
    private final UserProxy owner;
    private final UUID testId;
    private final TestProxy test;
    private String groupCode;
    private final Mark mark;
    private final LocalDateTime createdAt;

    /**
     * Constructs a {@code Result} instance with the specified details.
     *
     * @param id The unique identifier for this result.
     * @param ownerId The unique identifier of the user who owns this result.
     * @param owner A proxy for lazy-loading the user who owns this result.
     * @param testId The unique identifier of the associated test.
     * @param test A proxy for lazy-loading the associated test.
     * @param groupCode The code of the group associated with this result.
     * @param mark The mark received for the test.
     * @param createdAt The timestamp when the result was created.
     */
    public Result(UUID id, UUID ownerId, UserProxy owner, UUID testId, TestProxy test, String groupCode,
             Mark mark, LocalDateTime createdAt) {
        super(id);
        this.ownerId = ownerId;
        this.owner = owner;
        this.testId = testId;
        this.test = test;
        this.groupCode = groupCode;
        this.mark = mark;
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code ResultBuilderId} instance to start building a {@code Result}.
     *
     * @return A {@code ResultBuilderId} instance.
     */
    public static ResultBuilderId builder() {
        return id -> ownerId -> owner -> testId -> test -> groupCode -> mark -> createdAt -> () -> new Result(
                id, ownerId, owner, testId, test, groupCode, mark, createdAt);
    }

    /**
     * Interface for the {@code Result} builder to set the ID.
     */
    public interface ResultBuilderId {

        ResultBuilderOwnerId id(UUID id);
    }

    public interface ResultBuilderOwnerId {

        ResultBuilderOwner ownerId(UUID ownerId);
    }

    public interface ResultBuilderOwner {

        ResultBuilderTestId owner(UserProxy owner);
    }

    public interface ResultBuilderTestId {

        ResultBuilderTest testId(UUID testId);
    }

    public interface ResultBuilderTest {

        ResultBuilderGroupCode test(TestProxy test);
    }

    public interface ResultBuilderGroupCode {

        ResultBuilderMark groupCode(String groupCode);
    }

    public interface ResultBuilderMark {

        ResultBuilderCreatedAt mark(Mark mark);
    }


    public interface ResultBuilderCreatedAt {

        ResultBuilder createdAt(LocalDateTime createdAt);
    }


    public interface ResultBuilder {

        Result build();
    }

    public User getOwnerLazy() {
        return owner.get(ownerId);
    }

    public Test getTestLazy() {
        return test.get(testId);
    }

    public Mark getMark() {
        return mark;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public UUID getTestId() {
        return testId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    /**
     * Gets the creation timestamp of the result.
     *
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public int compareTo(Result o) {
        return this.createdAt.compareTo(o.createdAt);
    }
}
