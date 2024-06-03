package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.proxy.contract.Tags;
import com.tritonkor.persistence.repository.contract.TestRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The {@code TagsProxy} class serves as a proxy implementation for retrieving tags associated with a test.
 * It utilizes the Spring {@code ApplicationContext} to get the {@code TestRepository} bean.
 */
@Component
public class TagsProxy implements Tags {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a {@code TagsProxy} with the given application context.
     *
     * @param applicationContext the application context to use for retrieving the {@code TestRepository} bean
     */
    public TagsProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a list of tags associated with the specified test ID.
     *
     * @param testId the unique identifier of the test
     * @return a list of tags related to the specified test ID
     */
    @Override
    public List<Tag> get(UUID testId) {
        TestRepository testRepository = applicationContext.getBean(TestRepository.class);
        Tags tags = tId -> Collections.unmodifiableList(testRepository.findAllTags(tId));
        return tags.get(testId);
    }
}
