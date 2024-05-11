package org.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.transactionservice.common.TransactionType;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Long sourceAccountId;

    private Long destinationAccountId;

    private Double amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

}

