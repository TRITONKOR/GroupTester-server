package com.tritonkor.persistence.entity;

import com.tritonkor.persistence.entity.proxy.contract.TestProxy;
import com.tritonkor.persistence.entity.proxy.contract.UserProxy;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Result} class represents a result of a test for a specific user.
 */
public class Result extends Entity implements Comparable<Result> {

    private final UUID ownerId;
    private final UserProxy owner;
    private final UUID testId;
    private final TestProxy test;
    private final Mark mark;
    private final LocalDateTime createdAt;

    public Result(UUID id, UUID ownerId, UserProxy owner, UUID testId, TestProxy test,
             Mark mark, LocalDateTime createdAt) {
        super(id);
        this.ownerId = ownerId;
        this.owner = owner;
        this.testId = testId;
        this.test = test;
        this.mark = mark;
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code ResultBuilderId} instance to start building a {@code Result}.
     *
     * @return A {@code ResultBuilderId} instance.
     */
    public static ResultBuilderId builder() {
        return id -> ownerId -> owner -> testId -> test -> mark -> createdAt -> () -> new Result(
                id, ownerId, owner, testId, test, mark, createdAt);
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

        ResultBuilderMark test(TestProxy test);
    }

    /**
     * Interface for the {@code Result} builder to set the grade.
     */
    public interface ResultBuilderMark {

        ResultBuilderCreatedAt mark(Mark mark);
    }

    /**
     * Interface for the {@code Result} builder to set the creation date.
     */
    public interface ResultBuilderCreatedAt {

        ResultBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Result} builder.
     */
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