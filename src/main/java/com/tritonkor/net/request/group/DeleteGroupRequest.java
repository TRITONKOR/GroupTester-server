package com.tritonkor.net.request.group;

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
public class DeleteGroupRequest {
    @NotNull
    private UUID groupId;

    @NotNull
    private UUID userId;
}
