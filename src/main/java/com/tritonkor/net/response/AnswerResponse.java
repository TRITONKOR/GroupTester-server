package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponse {
    private String id;
    private String questionId;
    private String text;
    private Boolean isCorrect;

    public AnswerResponse(Answer answer) {
        this.id = answer.getId().toString();
        this.questionId = answer.getQuestionId().toString();
        this.text = answer.getText();
        this.isCorrect = answer.getCorrect();
    }
}
