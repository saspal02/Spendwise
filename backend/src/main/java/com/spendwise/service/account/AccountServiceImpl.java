package com.spendwise.service.account;

import com.spendwise.dto.AccountDto;
import com.spendwise.exception.AccountNotFoundException;
import com.spendwise.model.Account;
import com.spendwise.model.Card;
import com.spendwise.model.TransactionType;
import com.spendwise.repo.AccountRepo;
import com.spendwise.repo.CardRepo;
import com.spendwise.service.account.strategy.AccountBalanceStrategy;
import com.spendwise.service.account.strategy.AccountBalanceStrategyFactory;
import com.spendwise.service.paymentmode.PaymentModeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final CardRepo cardRepo;
    private final PaymentModeService paymentModeService;
    private final AccountBalanceStrategyFactory accountBalanceStrategyFactory;

    @Override
    public boolean existsByUserAndAccount(String appUserId, List<Long> accounts) {
        return accountRepo.existsByAppUserIdAndAccountId(appUserId, accounts, accounts.size());
    }

    @Transactional
    @Override
    public void updateBalance(Long accountId, Double amount, Long paymentModeId, String type, boolean isSourceAccount) {
        final var paymentMode = paymentModeService.get(paymentModeId);

        final var accountBalanceStrategy = accountBalanceStrategyFactory.getBalanceStrategy(paymentMode.getType());

        final var account = this.get(accountId);

        final var updatedBalance = accountBalanceStrategy.calculateBalance
                (account, amount, TransactionType.valueOf(type), isSourceAccount);

        account.setBalance(updatedBalance);
        this.update(account);
    }

    @Override
    public Account get(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    @Override
    public void update(Account account) {
        accountRepo.save(account);

    }

    @Override
    public void reverseBalance(Long accountId, Double amount, Long paymentModeId, String type, boolean isSourceAccount) {
        final var PaymentMode = paymentModeService.get(paymentModeId);
        final var accountBalanceStrategy = accountBalanceStrategyFactory.getBalanceStrategy(PaymentMode.getType());
        final var account = this.get(accountId);

        final var updatedBalance = accountBalanceStrategy.reverseBalance(account, amount, TransactionType.valueOf(type), isSourceAccount);
        account.setBalance(updatedBalance);
        this.update(account);

    }

    @Override
    public List<AccountDto> getAllAccounts(String userId) {

        List<AccountDto> dtos = new ArrayList<>();

        // 1. Fetch all accounts for user
        List<Account> accounts = accountRepo.findAllByAppUserId(userId);
        for (Account a : accounts) {
            if (a.getBank() != null) {
                dtos.add(new AccountDto(
                        String.valueOf(a.getId()),
                        a.getBank().getName(),
                        a.getLastFourDigits(),
                        "Savings",
                        a.getBalance()
                ));
            } else {
                dtos.add(new AccountDto(
                        String.valueOf(a.getId()),
                        "Wallet Cash",
                        "CASH".equals(a.getLastFourDigits()) ? "0000" : a.getLastFourDigits(),
                        "Cash",
                        a.getBalance()
                ));
            }
        }

        // 2. Fetch all cards for user
        List<Card> cards = cardRepo.findAllByAppUserId(userId);
        for (Card c: cards) {
            String bankName = (c.getAccount() != null && c.getAccount().getBank() != null)
                    ? c.getAccount().getBank().getName() + "Credit Card"
                    : "Credit Card";
            dtos.add(new AccountDto(
                    String.valueOf(c.getId()),
                    bankName,
                    c.getLastFourDigits(),
                    "Credit",
                    c.getCreditLimit()
            ));
        }

        return List.of();
    }


}
