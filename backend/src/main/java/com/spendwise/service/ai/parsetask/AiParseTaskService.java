package com.spendwise.service.ai.parsetask;

import com.spendwise.model.AiParsingTask;
import com.spendwise.model.Status;

import java.util.List;

public interface AiParseTaskService {

    AiParsingTask save(AiParsingTask aiParsingTask);

    List<AiParsingTask> getPendingTasks(Status status);

    AiParsingTask getIdWithAppUser(Long aLong);
}
