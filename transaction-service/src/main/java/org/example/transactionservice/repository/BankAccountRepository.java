package org.example.transactionservice.repository;

import org.example.transactionservice.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("SELECT ba FROM BankAccount ba WHERE ba.accountId = :id")
    BankAccount findBankAccoutById(Long id);
}
