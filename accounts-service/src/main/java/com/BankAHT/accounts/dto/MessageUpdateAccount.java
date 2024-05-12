package com.BankAHT.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageUpdateAccount {




    @JsonProperty("accountId")
    private Long AccountId;

    @JsonProperty("customerId")
    private Long CustomerId;

    @JsonProperty("accountType")
    private String AccountType;

    private Long Balance;

    @JsonProperty("enable")
    private Boolean enable;

    public  MessageUpdateAccount(Long accountId, Long customerId, String accountType, Boolean enable){
        this.AccountId= accountId;
        this.CustomerId= customerId;
        this.AccountType= accountType;
        this.enable= enable;
    }
}
