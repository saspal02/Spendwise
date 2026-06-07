package com.spendwise.service.account.strategy;

import com.spendwise.exception.InsufficientAccountBalanceException;
import com.spendwise.model.Account;
import com.spendwise.model.TransactionType;
import com.spendwise.service.transaction.TransactionBehaviour;

public interface AccountBalanceStrategy {
    Double calculateBalance(Account account, Double amount, TransactionType transactionType, boolean isSourceAccount) throws InsufficientAccountBalanceException;

    Double reverseBalance(Account account, Double previousAmount, TransactionType transactionType, boolean isSourceAccount);

    void validate(Account account, Double amount) throws InsufficientAccountBalanceException;

    TransactionBehaviour getType();

}
