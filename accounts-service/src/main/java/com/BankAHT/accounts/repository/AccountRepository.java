package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {
    @Query("SELECT a.CustomerId FROM Accounts a WHERE a.AccountId = :accountId")
    Long findCustomerIdByAccountId(@Param("accountId") Long accountId);

    @Transactional
    @Modifying
    @Query("UPDATE Accounts a SET a.Balance = :balance WHERE a.AccountId = :accountId ")
    void updateBalanceByAccountIdAndAmount(@Param("accountId") Long accountId, @Param("balance") Long balance);

    boolean existsById(Long id);
}
