package com.tritonkor.domain.dto;

import com.tritonkor.persistence.entity.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.nio.file.Path;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public record UserStoreDto (
        @NotBlank(message = "Ім'я користувача не може бути порожнім")
        @Length(min = 6, max = 32, message = "Ім'я користувача має містити від 6 до 32 символів")
        String username,

        @NotBlank(message = "Електронна пошта не може бути порожньою")
        @Email(message = "Неправильний формат електронної пошти")
        @Length(max = 128, message = "Email не повинен перевищувати 128 символів")
        String email,

        @NotBlank(message = "Пароль не може бути порожнім")
        @Length(min = 8, max = 72, message = "Пароль повинен містити від 8 до 72 символів")
        String password,

        Path avatar,

        @Past(message = "День народження має бути в минулому")
        LocalDate birthday,

        Role role
) {

}
