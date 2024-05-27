package com.tritonkor.persistence.entity.proxy.contract;

import com.tritonkor.persistence.entity.Test;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface Tests {
    List<Test> get(UUID tagId);
}
