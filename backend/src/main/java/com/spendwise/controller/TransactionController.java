package com.spendwise.controller;

import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.dto.TransactionDto;
import com.spendwise.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionRequestDto requestBody, @AuthenticationPrincipal String userId) {
        final var responseBody = transactionService.saveTransaction(userId,requestBody);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@AuthenticationPrincipal String userId) {
        final var responseBody = transactionService.getAllTransactions(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @PatchMapping
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody TransactionRequestDto requestBody, @AuthenticationPrincipal String userId) {

        final var responseBody = transactionService.updateTransaction(userId, requestBody);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId, @AuthenticationPrincipal String userId) {
        transactionService.deleteTransaction(userId, transactionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
