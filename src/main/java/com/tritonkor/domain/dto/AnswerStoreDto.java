package com.tritonkor.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;

public record AnswerStoreDto(
        @NotBlank(message = "Текст відповіді не може бути порожнім")
        @Length(min = 6, max = 256, message = "Текст відповіді має містити від 6 до 256 символів")
        String text,

        @NotNull(message = "Задайте питання, до якого належить відповідь")
        UUID questionId,

        @NotNull(message = "Задайте, чи відповідь є правильною")
        boolean isCorrect
) {

        @Override
        public String toString() {
                return "AnswerStoreDto{" +
                        "text='" + text + '\'' +
                        ", questionId=" + questionId +
                        ", isCorrect=" + isCorrect +
                        '}';
        }
}
