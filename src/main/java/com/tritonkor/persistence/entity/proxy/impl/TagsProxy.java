package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.proxy.contract.Tags;
import com.tritonkor.persistence.repository.contract.TestRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TagsProxy implements Tags {
    private final ApplicationContext applicationContext;

    public TagsProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public List<Tag> get(UUID testId) {
        TestRepository testRepository = applicationContext.getBean(TestRepository.class);
        Tags tags = tId -> Collections.unmodifiableList(testRepository.findAllTags(tId));
        return tags.get(testId);
    }
}
