package com.spendwise.service.ai.parsetask;

import com.spendwise.model.AiParsingTask;
import com.spendwise.model.Status;
import com.spendwise.repo.AiParsingTaskRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.spi.Limit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiParseTaskServiceImpl implements AiParseTaskService {

    private final AiParsingTaskRepo aiParsingTaskRepo;

    @Override
    public AiParsingTask save(AiParsingTask aiParsingTask) {
        return aiParsingTaskRepo.save(aiParsingTask);
    }

    @Override
    public List<AiParsingTask> getPendingTasks(Status status) {
        final var limit = new Limit();
        limit.setMaxRows(13);

        return aiParsingTaskRepo.findAllByStatusOrderByCreatedAtAsc(status, limit);
    }

    @Override
    public AiParsingTask getIdWithAppUser(Long jobId) {
        return aiParsingTaskRepo.findByIdWithAppUser(jobId)
                .orElseThrow(() -> new RuntimeException("Ai Parse Task not found with id: " + jobId));
    }
}
