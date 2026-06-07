package com.spendwise.service.paymentmode;

import com.spendwise.exception.PaymentModeNotFoundException;
import com.spendwise.model.PaymentMode;
import com.spendwise.repo.PaymentModeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentModeServiceImpl implements PaymentModeService {

    private final PaymentModeRepo paymentModeRepo;

    @Override
    public boolean existsById(Long paymentModeId) {
        return paymentModeRepo.existsById(paymentModeId);
    }

    @Override
    public PaymentMode get(Long paymentModeId) {
        return paymentModeRepo.findById(paymentModeId)
                .orElseThrow(() -> new PaymentModeNotFoundException(paymentModeId));
    }
}
