package com.spendwise.service.paymentmode;

import com.spendwise.model.PaymentMode;

public interface PaymentModeService {
    boolean existsById(Long paymentModeId);
    PaymentMode get(Long paymentModeId);
}
