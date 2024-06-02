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
public class ChangeUserStatusRequest {
    @NotNull
    @NotBlank
    private String groupCode;

    @NotNull
    private UUID userId;
}
