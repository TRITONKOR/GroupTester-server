package com.tritonkor.net.request.group;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChooseTestRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID groupId;

    @NotNull
    private UUID testId;
}
