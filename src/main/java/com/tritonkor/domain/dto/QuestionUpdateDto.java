package com.tritonkor.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;

public record QuestionUpdateDto(
        @NotNull(message = "Відсутній іденитфікатор питання")
        UUID id,
        @Length(min = 10, max = 256, message = "Текст питання повинний відповідати діапазону символів від 10 до 256")
        String text,

        byte[] image,
        UUID testId
) {

}
