package com.tritonkor.domain.service.impl;

import com.tritonkor.domain.dto.TagStoreDto;
import com.tritonkor.domain.exception.ValidationException;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.TagContext;
import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.filter.TagFilterDto;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.TagRepository;
import jakarta.validation.Validator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Service class for managing tags.
 */
@Service
public class TagService {

    private final TagContext tagContext;
    private final TagRepository tagRepository;
    private final Validator validator;

    /**
     * Constructs a new TagService with the given persistence context and validator.
     *
     * @param persistenceContext The persistence context.
     * @param validator          The validator.
     */
    public TagService(PersistenceContext persistenceContext, Validator validator) {
        this.tagContext = persistenceContext.tags;
        this.tagRepository = persistenceContext.tags.repository;
        this.validator = validator;
    }

    /**
     * Finds a tag by its ID.
     *
     * @param id The ID of the tag.
     * @return The tag found.
     * @throws EntityNotFoundException if the tag with the given ID is not found.
     */
    public Tag findById(UUID id) {
        return tagContext.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти тег"));
    }

    /**
     * Finds a tag by its name.
     *
     * @param name The name of the tag.
     * @return The tag found.
     * @throws EntityNotFoundException if the tag with the given name is not found.
     */
    public Tag findByName(String name) {
        return tagContext.repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти тег"));
    }

    /**
     * Finds all tags.
     *
     * @return The list of all tags.
     */
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    /**
     * Finds all tags with pagination and filtering options.
     *
     * @param offset       The offset for pagination.
     * @param limit        The limit for pagination.
     * @param sortColumn   The column to sort by.
     * @param ascending    The sorting order.
     * @param tagFilterDto The filtering criteria.
     * @return The list of tags based on the provided criteria.
     */
    public List<Tag> findAll(int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            TagFilterDto tagFilterDto) {
        return tagRepository.findAll(
                offset,
                limit,
                sortColumn,
                ascending,
                tagFilterDto);
    }

    /**
     * Counts the number of tags.
     *
     * @return The number of tags.
     */
    public long count() {
        return tagRepository.count();
    }

    /**
     * Creates a new tag.
     *
     * @param tagStoreDto The DTO containing information to create the tag.
     * @return The created tag.
     * @throws ValidationException if the tag data is not valid.
     */
    public Tag create(TagStoreDto tagStoreDto) {
        var violations = validator.validate(tagStoreDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("збереженні тесту", violations);
        }

        Tag tag = Tag.builder()
                .id(null)
                .name(tagStoreDto.name())
                .tests(null)
                .build();

        tagContext.registerNew(tag);
        tagContext.commit();
        return tagContext.getEntity();
    }

    public boolean delete(UUID id) {
        Tag tag = findById(id);

        return tagContext.repository.delete(tag.getId());
    }
}
