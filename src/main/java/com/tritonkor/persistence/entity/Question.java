package com.tritonkor.persistence.entity;

import com.tritonkor.persistence.entity.proxy.contract.Answers;
import com.tritonkor.persistence.entity.proxy.contract.TestProxy;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;

/**
 * The {@code Question} class represents a question with its associated properties.
 * This class contains the question text, optional image, and the associated answers.
 * It also maintains references to the related test through a proxy.
 */
@Getter
public class Question extends Entity implements Comparable<Question>{

    private String text;
    private Answers answers;
    private byte[] image;
    private final UUID testId;
    private final TestProxy test;

    /**
     * Constructs a {@code Question} instance with the specified details.
     *
     * @param id The unique identifier for this question.
     * @param text The text content of the question.
     * @param image The image associated with the question, if any.
     * @param answers The answers associated with the question.
     * @param testId The unique identifier of the associated test.
     * @param test A proxy for lazy-loading the associated test.
     */
    public Question(UUID id, String text, byte[] image, Answers answers, UUID testId, TestProxy test) {
        super(id);
        this.text = text;
        this.image = image;
        this.answers = answers;
        this.testId = testId;
        this.test = test;
    }

    /**
     * Returns a {@code QuestionBuilderId} instance to start building a {@code Question}.
     *
     * @return A {@code QuestionBuilderId} instance.
     */
    public static QuestionBuilderId builder() {
        return id -> answers -> testId -> test -> text -> image -> () -> new Question(id, answers, testId, test,
                text, image);
    }

    /**
     * Interface for the {@code Question} builder to set the ID.
     */
    public interface QuestionBuilderId {

        QuestionBuilderText id(UUID id);
    }

    public interface QuestionBuilderText {

        QuestionBuilderImage text(String text);
    }

    public interface QuestionBuilderImage {

        QuestionBuilderAnswers image(byte[] image);
    }

    public interface QuestionBuilderAnswers {

        QuestionBuilderTestId answers(Answers answers);
    }

    public interface QuestionBuilderTestId {

        QuestionBuilderTest testId(UUID testId);
    }

    public interface QuestionBuilderTest {

        QuestionBuilder test(TestProxy test);
    }

    /**
     * Interface for the final steps of the {@code Question} builder.
     */
    public interface QuestionBuilder {

        Question build();
    }


    public List<Answer> getAnswersLazy() {
        return answers.get(id);
    }

    public Test getTestLazy() {
        return test.get(testId);
    }


    @Override
    public int compareTo(Question o) {
        return this.text.compareTo(o.text);
    }
}
