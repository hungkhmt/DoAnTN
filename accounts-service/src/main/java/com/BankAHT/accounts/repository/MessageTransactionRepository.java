package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.dto.MessageTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTransactionRepository extends JpaRepository<MessageTransaction,Long> {
}
