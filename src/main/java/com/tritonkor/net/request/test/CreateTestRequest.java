package com.tritonkor.net.request.test;

import com.tritonkor.persistence.entity.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestRequest {
    @NotNull
    private UUID userId;

    @NotNull
    @NotBlank
    private String testTitle;

    @NotNull
    @Min(0)
    private int time;

    @NotNull
    private List<Tag> tags;
}
