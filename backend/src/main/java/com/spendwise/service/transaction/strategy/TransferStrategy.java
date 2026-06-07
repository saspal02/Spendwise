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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component("TransferStrategy")
@RequiredArgsConstructor
public class TransferStrategy implements TransactionTypeStrategy {

    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final TransactionRepo transactionRepo;

    @Override
    public TransactionDto process(String appUserId, TransactionRequestDto dto, OperationType type) throws InsufficientAccountBalanceException {
        Transaction debitTransaction;
        Transaction creditTransaction;
        String transferId;

        if (type == OperationType.UPDATE) {
            transferId = dto.transferId();

            if (Objects.isNull(transferId)){
                throw new RuntimeException("Transfer ID is null");
            }

            final var transactions = transactionRepo.findAllByTransferId(transferId);

            if (transactions.isEmpty() || transactions.size() < 2) {
                throw new RuntimeException("Unable to find any transactions for transferId: " + dto.transferId());


            }

            debitTransaction = transactions.stream()
                    .filter(e -> e.getAmount() < 0)
                    .findAny()
                    .orElseThrow(() -> new TransactionNotFoundException(dto.accountId()));

            creditTransaction = transactions.stream()
                    .filter(e -> e.getAmount() > 0)
                    .findAny()
                    .orElseThrow(() -> new TransactionNotFoundException(dto.accountId()));

            accountService.reverseBalance(debitTransaction.getAccount().getId(), Math.abs(debitTransaction.getAmount()),
                    dto.paymentModeId(), dto.type(), true);

            accountService.reverseBalance(creditTransaction.getAccount().getId(), Math.abs(creditTransaction.getAmount()),
                    dto.paymentModeId(), dto.type(), false);

        } else {
            debitTransaction = new Transaction();
            creditTransaction = new Transaction();
            transferId = UUID.randomUUID().toString();
        }

        accountService.updateBalance(dto.accountId(), dto.amount(), dto.paymentModeId(), dto.type(), true);

        accountService.updateBalance(dto.toAccountId(), dto.amount(), dto.paymentModeId(), dto.type(), false);

        transactionMapper.transactionFromRequestDto(dto, debitTransaction, appUserId, transferId, true);

        transactionMapper.transactionFromRequestDto(dto, creditTransaction, appUserId, transferId, false);

        transactionRepo.save(debitTransaction);

        final var savedTransaction = transactionRepo.save(creditTransaction);

        return transactionMapper.transactionToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }
}
