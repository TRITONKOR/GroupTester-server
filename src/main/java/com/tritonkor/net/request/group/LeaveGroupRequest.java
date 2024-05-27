package com.tritonkor.net.request.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveGroupRequest {
    @NotNull
    @NotBlank
    private String code;

    @NotNull
    private UUID userId;
}
