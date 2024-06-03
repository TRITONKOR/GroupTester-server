package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Test;
import com.tritonkor.persistence.entity.proxy.contract.Tests;
import com.tritonkor.persistence.repository.contract.TagRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The {@code TestsProxy} class serves as a proxy implementation for retrieving tests associated with a tag.
 * It utilizes the Spring {@code ApplicationContext} to get the {@code TagRepository} bean.
 */
@Component
public class TestsProxy implements Tests {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a {@code TestsProxy} with the given application context.
     *
     * @param applicationContext the application context to use for retrieving the {@code TagRepository} bean
     */
    public TestsProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a list of tests associated with the specified tag ID.
     *
     * @param tagId the unique identifier of the tag
     * @return a list of tests related to the specified tag ID
     */
    @Override
    public List<Test> get(UUID tagId) {
        TagRepository tagRepository = applicationContext.getBean(TagRepository.class);
        Tests tests = tId -> Collections.unmodifiableList(tagRepository.findAllTests(tId));
        return tests.get(tagId);
    }
}
