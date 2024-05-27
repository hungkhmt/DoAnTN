package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {
    @Query("SELECT a.customerId FROM Accounts a WHERE a.accountId = :accountId")
    Long findCustomerIdByAccountId(@Param("accountId") Long accountId);

    List<Accounts> findAllByCustomerId(Long customerId);
}
