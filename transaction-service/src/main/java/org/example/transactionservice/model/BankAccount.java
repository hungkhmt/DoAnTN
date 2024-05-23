package org.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.transactionservice.common.AccountType;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BankAccount {

    @Id
    private Long accountId;

    private Long userId;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

}

