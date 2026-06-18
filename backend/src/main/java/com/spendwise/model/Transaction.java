package com.spendwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Double amount;
    private LocalDate transactionDate;
    private String transferId;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "category")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_model_id")
    private PaymentMode paymentMode;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Set<AiParsingTask> aiparsingTaskSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    private Long createdAt = System.currentTimeMillis();
    private Long updatedAt;


}
