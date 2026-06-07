package com.spendwise.service.transaction;

import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.dto.TransactionDto;
import com.spendwise.exception.InsufficientAccountBalanceException;

import java.util.List;

public interface TransactionService {
    TransactionDto saveTransaction(String appUserId, TransactionRequestDto requestBody) throws InsufficientAccountBalanceException;

    List<TransactionDto> getAllTransactions(String appUserId);

    TransactionDto updateTransaction(String appUserId, TransactionRequestDto requestBody) throws InsufficientAccountBalanceException;

    void deleteTransaction(String appUserId, Long transactionId);
}
