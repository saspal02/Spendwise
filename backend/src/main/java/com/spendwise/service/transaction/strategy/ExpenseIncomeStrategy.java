package com.spendwise.service.transaction.strategy;

import com.spendwise.dto.TransactionDto;
import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.exception.InsufficientAccountBalanceException;
import com.spendwise.exception.TransactionNotFoundException;
import com.spendwise.mapper.TransactionMapper;
import com.spendwise.model.Transaction;
import com.spendwise.model.TransactionType;
import com.spendwise.repo.TransactionRepo;
import com.spendwise.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("ExpenseIncomeStrategy")
@RequiredArgsConstructor
public class ExpenseIncomeStrategy implements TransactionTypeStrategy {

    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final TransactionRepo transactionRepo;

    @Transactional
    @Override
    public TransactionDto process(String appUserId, TransactionRequestDto dto, OperationType type) throws InsufficientAccountBalanceException {
        Transaction transaction;

        if (type == OperationType.UPDATE) {
            transaction = transactionRepo.findById(dto.transactionId())
                    .orElseThrow(() -> new TransactionNotFoundException(dto.transactionId()));

            accountService.reverseBalance(dto.accountId(), Math.abs(transaction.getAmount()), dto.paymentModeId(), dto.type(), false);

        } else {
            transaction = new Transaction();
        }

        accountService.updateBalance(dto.accountId(), dto.amount(), dto.paymentModeId(), dto.type(), false);

        transactionMapper.transactionFromRequestDto(dto, transaction, appUserId, null, false);

        final var savedTransaction = transactionRepo.save(transaction);

        return transactionMapper.transactionToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.INCOME;
    }
}
