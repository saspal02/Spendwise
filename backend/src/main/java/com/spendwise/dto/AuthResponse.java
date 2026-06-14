package com.spendwise.dto;

public record AuthResponse(String accessToken, String tokenType, long expiresInSeconds) {
}
