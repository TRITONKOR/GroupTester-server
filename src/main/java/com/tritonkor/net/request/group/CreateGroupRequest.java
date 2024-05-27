package com.tritonkor.net.request.group;

import jakarta.validation.constraints.NotBlank;
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
public class CreateGroupRequest {
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String code;

    @NotNull
    private UUID userId;
}
