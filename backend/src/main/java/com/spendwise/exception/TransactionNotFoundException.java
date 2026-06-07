package com.spendwise.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long accountId) {
        super("Transaction not found with id " + accountId);
    }
}
