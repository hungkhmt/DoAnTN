package com.BankAHT.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

}
