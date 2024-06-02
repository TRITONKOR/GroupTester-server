package com.tritonkor.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;

public record AnswerUpdateDto(
        @NotNull(message = "Відсутній іденитфікатор відповіді")
        UUID id,

        @Length(min = 6, max = 32, message = "Текст відповіді має містити від 6 до 32 символів")
        String text,

        UUID questionId,

        boolean isCorrect
) {

}
