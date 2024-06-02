package com.tritonkor.net.request.question;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteQuestionRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID questionId;
}
