package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Question;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponse {
    private String id;
    private String testId;
    private String text;
    private byte[] image;
    private List<AnswerResponse> answers;

    public QuestionResponse(Question question) {
        this.id = question.getId().toString();
        this.testId = question.getTestId().toString();
        this.text = question.getText();
        this.image = question.getImage();
        this.answers = question.getAnswersLazy().stream()
                .map(AnswerResponse::new)
                .collect(Collectors.toList());
    }
}
