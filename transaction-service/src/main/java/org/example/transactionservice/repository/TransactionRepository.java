package org.example.transactionservice.repository;

import org.example.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> , PagingAndSortingRepository<Transaction, Long> {

    @Query("SELECT tr FROM Transaction tr WHERE tr.sourceAccountId =:id OR tr.destinationAccountId =:id")
    List<Transaction> findByIdAccounts(Long id);
}
