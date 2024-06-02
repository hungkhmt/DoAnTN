package org.example.transactionservice.repository;

import org.example.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> , PagingAndSortingRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM Transaction t WHERE MONTH(t.transaction_date) = :month", nativeQuery = true)
    List<Transaction> findAllByMonth(@Param("month") int month);

    @Query("SELECT tr FROM Transaction tr WHERE tr.sourceAccountId =:id OR tr.destinationAccountId =:id")
    List<Transaction> findByIdAccounts(Long id);

    @Query(value = "SELECT * FROM Transaction t WHERE t.source_account_id = :sourceAccountId AND MONTH(t.transaction_date) = :month", nativeQuery = true)
    List<Transaction> findBySourceAccountId(@Param("sourceAccountId") Long sourceAccountId, @Param("month") int month);

    @Query(value = "SELECT * FROM Transaction t WHERE t.destination_account_id = :destinationAccountId AND MONTH(t.transaction_date) = :month", nativeQuery = true)
    List<Transaction> findByDestinationAccountId(@Param("destinationAccountId") Long destinationAccountId, @Param("month") int month);

    @Query("SELECT tr FROM Transaction tr JOIN BankAccount a ON tr.sourceAccountId = a.accountId OR tr.destinationAccountId = a.accountId WHERE a.userId =:userId ORDER BY tr.transactionId ASC")
    List<Transaction> findByUserId(Long userId);
}