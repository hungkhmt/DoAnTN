package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {
    @Query("SELECT a.CustomerId FROM Accounts a WHERE a.AccountId = :accountId")
    Long findCustomerIdByAccountId(@Param("accountId") Long accountId);
}
