package com.spendwise.controller;

import com.spendwise.dto.TransactionRequestDto;
import com.spendwise.dto.TransactionDto;
import com.spendwise.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private static final String LOGGED_IN_USER = "a1029488-f4a8-4466-bada-884994cd22ad";

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionRequestDto requestBody) {
        final var responseBody = transactionService.saveTransaction(LOGGED_IN_USER,requestBody);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        final var responseBody = transactionService.getAllTransactions(LOGGED_IN_USER);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @PatchMapping
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody TransactionRequestDto requestBody) {

        final var responseBody = transactionService.updateTransaction(LOGGED_IN_USER, requestBody);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(LOGGED_IN_USER, transactionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
