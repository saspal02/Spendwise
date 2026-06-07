package com.spendwise.service.transaction;

import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.dto.TransactionDto;
import com.spendwise.exception.*;
import com.spendwise.mapper.TransactionMapper;
import com.spendwise.model.*;
import com.spendwise.repo.TransactionRepo;
import com.spendwise.service.account.AccountService;
import com.spendwise.service.appuser.AppUserService;
import com.spendwise.service.category.CategoryService;
import com.spendwise.service.paymentmode.PaymentModeService;
import com.spendwise.service.transaction.strategy.OperationType;
import com.spendwise.service.transaction.strategy.TxnTypeStrategyFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final PaymentModeService paymentModeService;
    private final TransactionRepo transactionRepo;
    private final TransactionMapper transactionMapper;
    private final TxnTypeStrategyFactory txnTypeStrategyFactory;


    @Transactional
    @Override
    public TransactionDto saveTransaction(String appUserId, TransactionRequestDto requestBody) {

       getAndValidateAccounts(requestBody, appUserId);

        final var transactionType =
                TransactionType.valueOf(requestBody.type()) ==
                        TransactionType.TRANSFER? TransactionType.TRANSFER: TransactionType.INCOME;

        final var strategy = txnTypeStrategyFactory.getStrategy(transactionType);

        return strategy.process(appUserId, requestBody, OperationType.CREATE);
        
    }

    public List<TransactionDto> getAllTransactions(String appUserId) {
        final var transactions =  transactionRepo.findAllByAppUser(appUserId);
        return transactionMapper.transactionToTransactionDto(transactions);
    }

    @Override
    public TransactionDto updateTransaction(String appUserId, TransactionRequestDto requestBody) throws InsufficientAccountBalanceException {
        getAndValidateAccounts(requestBody, appUserId);

        final var transactionType = TransactionType.valueOf(requestBody.type()) == TransactionType.TRANSFER?
                TransactionType.TRANSFER: TransactionType.INCOME;

        final var strategy = txnTypeStrategyFactory.getStrategy(transactionType);

        return strategy.process(appUserId, requestBody, OperationType.UPDATE);

    }

    @Override
    public void deleteTransaction(String appUserId, Long transactionId) {
        transactionRepo.deleteByTransactionIdAndAppUserId(transactionId, appUserId);

    }

    // Helper functions to validate accounts
    private void getAndValidateAccounts(TransactionRequestDto dto, String appUserId) {
        final var accountId = dto.accountId();
        final var categoryId = dto.categoryId();
        final var paymentModeId = dto.paymentModeId();
        final var toAccountId = dto.toAccountId();
        final var type = dto.type();

        final var accounts = getAccounts(accountId, toAccountId, type);

        validateAccountCategoryAndPaymentMode(appUserId, accounts, categoryId, paymentModeId);
    }

    private static List<Long> getAccounts(Long accountId, Long toAccountId, String type) {
        List<Long> accounts = new ArrayList<>();
        accounts.add(accountId);

        if (TransactionType.valueOf(type) == TransactionType.TRANSFER) {
            accounts.add(toAccountId);
        }

        return accounts;
    }

    private void validateAccountCategoryAndPaymentMode(String appUserId, List<Long> accounts, Long categoryId, Long paymentModeId) {
        final var accountExists = accountService.existsByUserAndAccount(appUserId, accounts);
        if (!accountExists) {
            throw new AccountNotOwnedByUserException(accounts, appUserId);
        }
        final var categoryExists = categoryService.existsByUserAndCategory(appUserId, categoryId);

        if (!categoryExists) {
            throw new CategoryNotFoundException(categoryId);
        }
        final var paymentModeExists = paymentModeService.existsById(paymentModeId);
        if (!paymentModeExists) {
            throw new PaymentModeNotFoundException(paymentModeId);
        }
    }


}
