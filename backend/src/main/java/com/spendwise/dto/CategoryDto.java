package com.spendwise.dto;

public record CategoryDto(
        Long id,
        String name,
        boolean isSystem
) {
}
