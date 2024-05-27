package com.tritonkor.net.request;

import com.tritonkor.persistence.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Логін є обов'язковим")
    @Size(min = 4, max = 64, message = "Довжина логіну має бути в межах від 4 до 64 символів")
    private String username;

    @NotBlank(message = "Пароль є обов'язковим")
    @Size(min = 4, max = 64, message = "Довжина паролю має бути в межах від 4 до 64 символів")
    private String password;

    @NotBlank(message = "Імейл є обов'язковим")
    @Email
    private String email;

    @NotNull(message = "День народження є обов'язковим")
    @Past(message = "День народження повинний бути в минулому")
    private LocalDate birthday;

    private byte[] avatar;

    @NotNull(message = "Роль є обов'язковою")
    private User.Role role;
}
