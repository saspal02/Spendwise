package com.spendwise.service.ai;

import com.spendwise.model.Transaction;
import com.spendwise.model.TransactionType;

public record AiParseResult(
        TransactionType type,
        String description,
        Double amount,
        String date,
        String errorMessage,
        String category


) {


}
