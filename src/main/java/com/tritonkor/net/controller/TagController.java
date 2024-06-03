package com.tritonkor.net.controller;

import com.tritonkor.domain.service.impl.TagService;
import com.tritonkor.net.response.TagResponse;
import com.tritonkor.persistence.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code TagController} class handles tag-related HTTP requests.
 */
@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    /**
     * Retrieves all tags.
     *
     * @return a response entity containing a list of tag responses
     */
    @GetMapping("/all")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.findAll();

        if (tags.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TagResponse> tagResponses = new ArrayList<>();
        for (Tag tag : tags) {
            tagResponses.add(new TagResponse(tag));
        }

        return ResponseEntity.ok(tagResponses);
    }

}
