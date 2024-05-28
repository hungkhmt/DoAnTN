package com.BankAHT.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Accounts extends BaseEntity{

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "balance")
    private Long balance;

    private Double maxTransactionAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
