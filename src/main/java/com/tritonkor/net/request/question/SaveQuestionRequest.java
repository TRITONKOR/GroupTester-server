package com.tritonkor.net.request.question;

import com.tritonkor.net.response.AnswerResponse;
import com.tritonkor.persistence.entity.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuestionRequest {

    private UUID id;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID testId;

    @NotNull
    @NotBlank
    private String text;


    private byte[] image;

    @NotNull
    private List<Answer> answers;
}
