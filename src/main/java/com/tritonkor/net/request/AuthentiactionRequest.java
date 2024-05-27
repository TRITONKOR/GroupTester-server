package com.tritonkor.net.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthentiactionRequest {
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String password;
}
