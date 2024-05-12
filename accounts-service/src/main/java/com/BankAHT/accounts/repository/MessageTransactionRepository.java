package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.dto.MessageUpdateBalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTransactionRepository extends JpaRepository<MessageUpdateBalanceTransaction,Long> {
}
