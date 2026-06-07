package com.spendwise.exception;

public class PaymentModeNotFoundException extends RuntimeException {
    public PaymentModeNotFoundException(Long paymentModeId) {
        super("Payment Mode not found with id " + paymentModeId);
    }
}
