package org.example.transactionservice.repository;

import org.example.transactionservice.dto.transaction.MessageUpdateBalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageTransactionRepository extends JpaRepository<MessageUpdateBalanceTransaction,Long> {
    public List<MessageUpdateBalanceTransaction> findByStatus(Boolean status);
}
