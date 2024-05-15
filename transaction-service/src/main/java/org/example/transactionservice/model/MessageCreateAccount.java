package org.example.transactionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageCreateAccount {
//    @Id
//    private String message;
     private Long AccountId;

    private Long CustomerId;

    private String AccountType;

    private Long Balance;

    private Boolean enable;

    public  MessageCreateAccount(Long accountId, Long customerId, String accountType, Boolean enable){
        this.AccountId= accountId;
        this.CustomerId= customerId;
        this.AccountType= accountType;
        this.enable= enable;
    }
}
