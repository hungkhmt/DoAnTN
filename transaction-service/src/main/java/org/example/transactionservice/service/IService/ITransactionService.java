package org.example.transactionservice.service.IService;

import org.example.transactionservice.dto.transaction.DepositDto;
import org.example.transactionservice.dto.transaction.TransferDto;
import org.example.transactionservice.dto.transaction.WithdrawDto;
import org.example.transactionservice.model.Transaction;
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
    List<Transaction> findAll();


    List<Transaction> findByIdAccounts(Long id);
}
