package com.spendwise.event;

import com.spendwise.model.AiParsingTask;

public record AiParsingTaskCompleted(
        Long jobId,
        AiParsingTask task
) {
}
