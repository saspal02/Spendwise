package com.spendwise.service.transaction.strategy;

import com.spendwise.dto.TransactionDto;
import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.exception.InsufficientAccountBalanceException;
import com.spendwise.model.TransactionType;

public interface TransactionTypeStrategy {

    TransactionDto process(String appUserId, TransactionRequestDto dto, OperationType type) throws InsufficientAccountBalanceException;

    TransactionType getType();
 }
