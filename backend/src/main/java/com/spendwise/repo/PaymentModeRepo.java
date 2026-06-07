package com.spendwise.repo;

import com.spendwise.model.PaymentMode;
import org.springframework.data.repository.CrudRepository;

public interface PaymentModeRepo extends CrudRepository<PaymentMode, Long> {
}
