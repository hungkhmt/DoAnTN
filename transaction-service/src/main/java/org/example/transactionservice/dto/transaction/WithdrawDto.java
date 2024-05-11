package org.example.transactionservice.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawDto {

    private Long accountId;

    private Double amount;

    private String description;
}
