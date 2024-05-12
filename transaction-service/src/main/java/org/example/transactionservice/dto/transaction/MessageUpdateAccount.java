package org.example.transactionservice.dto.transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageUpdateAccount {




    private Long AccountId;

    private Long CustomerId;

    private String AccountType;

    private Long Balance;

    private Boolean enable;

    public  MessageUpdateAccount(Long accountId, Long customerId, String accountType, Boolean enable){
        this.AccountId= accountId;
        this.CustomerId= customerId;
        this.AccountType= accountType;
        this.enable= enable;
    }
}