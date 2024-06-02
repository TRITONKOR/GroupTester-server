package com.tritonkor.net.request.test;

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
public class DeleteTestRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID testId;
}
