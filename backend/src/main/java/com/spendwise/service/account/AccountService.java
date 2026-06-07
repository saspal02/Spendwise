package com.spendwise.service.account;

import com.spendwise.model.Account;

import java.util.List;

public interface AccountService  {
    boolean existsByUserAndAccount(String appUserId, List<Long> accounts);

    void updateBalance(Long accountId, Double amount, Long paymentModeId, String type, boolean isSourceAccount);

    Account get(Long accountId);

    void update(Account account);

    void reverseBalance(Long accountId, Double amount, Long paymentModeId, String type, boolean isSourceAccount);


}
