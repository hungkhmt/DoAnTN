package org.example.transactionservice.service.IService;

import org.example.transactionservice.dto.transaction.DepositDto;
import org.example.transactionservice.dto.transaction.TransferDto;
import org.example.transactionservice.dto.transaction.WithdrawDto;
import org.example.transactionservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITransactionService {

    // function handle transfer money
    Transaction transfer(TransferDto transferDto) throws Exception;

    // function handle withdraw money
    Transaction withdraw(WithdrawDto withdrawDto) throws Exception;

    // function handle deposit money
    Transaction deposit(DepositDto depositDto) throws Exception;
    List<Transaction> findAllByMonth(Integer mounth);

    List<Transaction> findAllTransaction();
    Page<Transaction> findByIdAccounts(Long id, Pageable pageable);
    List<Transaction> findBySourceAccountId(Long id, Integer mounth);
    List<Transaction> findByDestinationAccountId(Long id, Integer mounth);
    List<Transaction> findByIdCustomer(Long id);
}
