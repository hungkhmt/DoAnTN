package org.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.transactionservice.common.CurrencySymbol;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Currency {

    @Id
    private String currencyCode;

    private String name;

    @Enumerated(EnumType.STRING)
    private CurrencySymbol symbol;

}

