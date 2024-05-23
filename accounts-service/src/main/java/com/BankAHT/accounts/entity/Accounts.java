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
    private Long AccountId;

    @Column(name = "customer_id")
    private Long CustomerId;

    @Column(name = "account_type")
    private String AccountType;

    @Column(name = "balance")
    private Long Balance;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
