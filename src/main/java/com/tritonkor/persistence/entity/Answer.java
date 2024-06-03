package com.tritonkor.persistence.entity;

import com.tritonkor.persistence.entity.proxy.contract.QuestionProxy;
import java.util.UUID;

/**
 * The {@code Answer} class represents an answer entity with its associated properties.
 * This class contains information about the answer text, its relation to a question,
 * and whether it is correct.
 * It implements the {@link Comparable} interface to allow comparison based on the text content.
 */
public class Answer extends Entity implements Comparable<Answer> {

    private UUID questionId;
    private QuestionProxy question;
    private String text;
    private Boolean isCorrect;

    /**
     * Constructs an {@code Answer} instance with the specified details.
     *
     * @param id The unique identifier for this answer.
     * @param questionId The unique identifier of the associated question.
     * @param question A proxy for lazy-loading the associated question.
     * @param text The text content of the answer.
     * @param isCorrect Indicates whether this answer is correct.
     */
    public Answer(UUID id, UUID questionId, QuestionProxy question, String text,
            Boolean isCorrect) {
        super(id);
        this.questionId = questionId;
        this.question = question;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    /**
     * Default constructor for {@code Answer} which initializes the ID to {@code null}.
     */
    public Answer() {
        super(null);
    }

    /**
     * Provides a builder for creating instances of {@code Answer}.
     *
     * @return An {@code AnswerBuilderId} instance to start building an {@code Answer}.
     */
    public static AnswerBuilderId builder() {
        return id -> questionId -> question -> text -> isCorrect -> () -> new Answer(id,
                questionId, question, text, isCorrect);
    }

    /**
     * Interface for the {@code Answer} builder to set the ID.
     */
    public interface AnswerBuilderId {

        AnswerBuilderQuestionId id(UUID id);
    }

    public interface AnswerBuilderQuestionId {

        AnswerBuilderQuestion questionId(UUID questionId);
    }

    public interface AnswerBuilderQuestion {

        AnswerBuilderText question(QuestionProxy question);
    }

    /**
     * Interface for the {@code Answer} builder to set the text.
     */
    public interface AnswerBuilderText {

        AnswerBuilderIsCorrect text(String text);
    }

    public interface AnswerBuilderIsCorrect {

        AnswerBuilder isCorrect(Boolean isCorrect);
    }

    /**
     * Interface for the final steps of the {@code Answer} builder.
     */
    public interface AnswerBuilder {

        Answer build();
    }

    /**
     * Gets the text content of the answer.
     *
     * @return The text content of the answer.
     */
    public String getText() {
        return text;
    }

    /**
     * Lazily loads and gets the associated {@code Question}.
     *
     * @return The associated {@code Question} object.
     */
    public Question getQuestionLazy() {
        return question.get(questionId);
    }

    /**
     * Checks if the answer is correct.
     *
     * @return {@code true} if the answer is correct, otherwise {@code false}.
     */
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * Gets the unique identifier of the associated question.
     *
     * @return The unique identifier of the question.
     */
    public UUID getQuestionId() {
        return questionId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "questionId=" + questionId +
                ", question=" + question +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    @Override
    public int compareTo(Answer o) {
        return this.text.compareTo(o.text);
    }
}
