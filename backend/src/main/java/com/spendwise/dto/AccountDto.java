package com.spendwise.dto;

public record AccountDto
        (String id,
         String bankName,
         String lastFour,
         String type,
         Double amount) {

}
