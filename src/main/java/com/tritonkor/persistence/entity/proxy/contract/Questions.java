package com.tritonkor.persistence.entity.proxy.contract;

import com.tritonkor.persistence.entity.Question;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface Questions {
    List<Question> get(UUID testId);
}
