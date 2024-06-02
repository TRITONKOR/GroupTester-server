package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Mark;
import com.tritonkor.persistence.entity.Result;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponse {
    private UUID id;
    private UserResponse user;
    private TestResponse test;
    private String groupCode;
    private Mark mark;
    private LocalDateTime create_date;

    public ResultResponse(Result result) {
        this.id = result.getId();
        this.user = new UserResponse(result.getOwnerLazy());
        this.test = new TestResponse(result.getTestLazy());
        this.groupCode = result.getGroupCode();
        this.mark = result.getMark();
        this.create_date = result.getCreatedAt();
    }
}
