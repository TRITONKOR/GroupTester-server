package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse {
    private String id;
    private String name;

    public TagResponse(Tag tag) {
        this.id = tag.getId().toString();
        this.name = tag.getName();
    }
}
