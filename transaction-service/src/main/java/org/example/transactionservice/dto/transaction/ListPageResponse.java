package org.example.transactionservice.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.transactionservice.model.Transaction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListPageResponse {
    private List<Transaction> transactionList;
    private int totalPage;
}
