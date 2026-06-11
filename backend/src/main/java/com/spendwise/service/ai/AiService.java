package com.spendwise.service.ai;

import com.spendwise.dto.AiInputDto;
import com.spendwise.dto.AiTaskDto;
import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.model.AiParsingTask;

public interface AiService {

    TransactionRequestDto parse(AiInputDto requestBody);

    void parse(AiParsingTask task);

    AiTaskDto save(String appUserId, AiInputDto requestBody);
}
