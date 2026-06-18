package com.spendwise.repo;

import com.spendwise.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByTransferId(String transferId);

    @Query("SELECT t " +
            "FROM Transaction t " +
            "WHERE t.appUser.id = :appUserId")
    List<Transaction> findAllByAppUser(String appUserId);

    @Modifying
    @Transactional
    @Query("DELETE " +
            "FROM Transaction t " +
            "WHERE t.appUser.id = :appUserId " +
            "AND t.id = :transactionId")
    void deleteByTransactionIdAndAppUserId(Long transactionId, String appUserId);

    List<Transaction> findAllByAppUserIdOrderByTransactionDateDesc(String appUserId, Pageable pageable);

    @Query("SELECT t from Transaction t WHERE t.appUser.id = :appUserId AND t.transactionDate >= :startDate ORDER BY t.transactionDate DESC")
    List<Transaction> findRecentTransactions(@Param("appUserId") String appUserId, @Param("startDate") LocalDate startDate);



}
