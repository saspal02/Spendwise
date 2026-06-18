package com.spendwise.dto;

import com.spendwise.model.Status;

public record AiActiveTaskDto(
        String jobId,
        Status status
) {
}
