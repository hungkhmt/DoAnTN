package org.example.transactionservice.repository;

import org.example.transactionservice.dto.transaction.MessageTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageTransactionRepository extends JpaRepository<MessageTransaction,Long> {
    public List<MessageTransaction> findByStatus(Boolean status);
}
