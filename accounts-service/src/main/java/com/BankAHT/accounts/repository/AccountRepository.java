package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {
    @Query(value = "SELECT * FROM Accounts t WHERE MONTH(t.created_at) = :month", nativeQuery = true)
    List<Accounts> findAllByMonth(@Param("month") int month);

    @Query("SELECT a.customerId FROM Accounts a WHERE a.accountId = :accountId")
    Long findCustomerIdByAccountId(@Param("accountId") Long accountId);
    List<Accounts> findAllByCustomerId(Long customerId);
    @Transactional
    @Modifying
    @Query("UPDATE Accounts a SET a.balance = :balance WHERE a.accountId = :accountId ")
    void updateBalanceByAccountIdAndAmount(@Param("accountId") Long accountId, @Param("balance") Long balance);
    boolean existsById(Long id);
}
