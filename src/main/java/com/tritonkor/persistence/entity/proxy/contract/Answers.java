package com.tritonkor.persistence.entity.proxy.contract;

import com.tritonkor.persistence.entity.Answer;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface Answers {
    List<Answer> get(UUID questionId);
}
