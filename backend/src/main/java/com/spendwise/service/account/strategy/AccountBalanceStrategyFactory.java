package com.spendwise.service.account.strategy;

import com.spendwise.service.transaction.TransactionBehaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AccountBalanceStrategyFactory {
    private final Map<TransactionBehaviour, AccountBalanceStrategy> balanceStrategies;

    @Autowired
    public AccountBalanceStrategyFactory(List<AccountBalanceStrategy> balanceStrategies) {
        this.balanceStrategies = balanceStrategies.stream()
                .collect(Collectors.toMap(
                        AccountBalanceStrategy::getType,
                        e -> e
                ));
    }

    public AccountBalanceStrategy getBalanceStrategy(TransactionBehaviour type) {
        return balanceStrategies.get(type);
    }
}
