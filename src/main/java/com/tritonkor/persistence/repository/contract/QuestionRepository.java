package com.tritonkor.persistence.repository.contract;

import com.tritonkor.persistence.entity.Question;
import com.tritonkor.persistence.entity.filter.QuestionFilterDto;
import com.tritonkor.persistence.repository.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface QuestionRepository extends Repository<Question> {
    Optional<Question> findByText(String text);
    List<Question> findAllByTestId(UUID testId);

    List<Question> findAll(int offset, int limit, String sortColumn, boolean ascending, QuestionFilterDto questionFilterDto);
}
