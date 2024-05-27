package com.tritonkor.persistence.repository;

import com.tritonkor.persistence.entity.Entity;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends Entity> {
    Optional<T> findById(UUID id);

    Optional<T> findBy(String column, Object value);

    List<T> findAll();

    List<T> findAll(int offset, int limit);

    List<T> findAll(int offset, int limit, String sortColumn, boolean ascending);

    List<T> findAll(int offset, int limit, String sortColumn, boolean ascending,
            Map<String, Object> filters);

    List<T> findAll(int offset, int limit, String sortColumn, boolean ascending,
            Map<String, Object> filters, String where);

    List<T> findAllWhere(String whereQuery);

    long count();

    T save(T entity);

    List<T> save(Collection<T> entities);

    boolean delete(UUID id);

    boolean delete(Collection<UUID> ids);
}
