package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResponse {
    private String id;
    private String title;
    private List<QuestionResponse> questions;
    private List<TagResponse> tags;
    private LocalDateTime createDate;

    public TestResponse(Test test) {
        this.id = test.getId().toString();
        this.title = test.getTitle();
        this.questions = test.getQuestionsLazy().stream()
                .map(QuestionResponse::new)
                .collect(Collectors.toList());
        this.tags = test.getTagsLazy().stream()
                .map(TagResponse::new)
                .collect(Collectors.toList());
        this.createDate = test.getCreatedAt();
    }
}
