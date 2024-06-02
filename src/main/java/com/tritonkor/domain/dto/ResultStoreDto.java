package com.tritonkor.domain.dto;

import com.tritonkor.persistence.entity.Mark;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ResultStoreDto(
        @NotNull(message = "Задайте тест, до якого належить результат")
        UUID testId,

        @NotNull(message = "Задайте користувача, якому належить результат")
        UUID ownerId,

        @NotBlank(message = "Задайте користувача, якому належить результат")
        String groupCode,

        @NotNull(message = "У результата має бути оцінка")
        Mark mark
) {

}
