package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Answer;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponse {
    private UUID id;
    private UUID questionId;
    private String text;
    private Boolean isCorrect;

    public AnswerResponse(Answer answer) {
        this.id = answer.getId();
        this.questionId = answer.getQuestionId();
        this.text = answer.getText();
        this.isCorrect = answer.getIsCorrect();
    }

    public AnswerResponse(){}

    @Override
    public String toString() {
        return "AnswerResponse{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
