package org.example.transactionservice.service.implement;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.transactionservice.common.TransactionType;
import org.example.transactionservice.dto.transaction.DepositDto;
import org.example.transactionservice.dto.transaction.TransferDto;
import org.example.transactionservice.dto.transaction.WithdrawDto;
import org.example.transactionservice.exception.InsufficientBalanceException;
import org.example.transactionservice.model.BankAccount;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.repository.TransactionRepository;
import org.example.transactionservice.service.IService.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService {

    @Autowired
    private final BankAccountService bankAccountService;

    @Autowired
    private final TransactionRepository transactionRepository;


    // handle transfer action
    @Transactional
    @Override
    public Transaction transfer(TransferDto transferDto) throws Exception {
        BankAccount sourceAcc = bankAccountService.getBankAccById(transferDto.getSourceAccountId());
        BankAccount destinationAcc = bankAccountService.getBankAccById(transferDto.getDestinationAccountId());

        // check if balace less than amount of money want to transfer
        if (sourceAcc.getBalance() < transferDto.getAmount() || sourceAcc.getBalance() < 0 || sourceAcc.getBalance()<50_000 || sourceAcc.getBalance()-transferDto.getAmount() < 50_000
                || transferDto.getAmount() > sourceAcc.getMaxTransactionAmount()
                || transferDto.getAmount() < 1000
        ) {
            throw new InsufficientBalanceException("Insufficient balance to perform the transaction or the amount exceeds the transaction limit or is below the minimum required");
        }

        // create transaction
        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(transferDto.getSourceAccountId());
        transaction.setDestinationAccountId(transferDto.getDestinationAccountId());
        transaction.setAmount(transferDto.getAmount());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription(transferDto.getDescription());

        transactionRepository.save(transaction);
        bankAccountService.updateAccountBalance(transferDto.getSourceAccountId(), -transferDto.getAmount());
        bankAccountService.updateAccountBalance(transferDto.getDestinationAccountId(), transferDto.getAmount());
        return transaction;
    }


    // function handle withdraw
    @Transactional
    @Override
    public Transaction withdraw(WithdrawDto withdrawDto) throws Exception {
        BankAccount sourceAcc = bankAccountService.getBankAccById(withdrawDto.getAccountId());

        if (sourceAcc.getBalance() < withdrawDto.getAmount()|| sourceAcc.getBalance() - withdrawDto.getAmount() < 50_000) {
            throw new InsufficientBalanceException("Insufficient balance to perform the transaction");
        }

        Transaction transaction = Transaction
                .builder()
                .sourceAccountId(sourceAcc.getAccountId())
                .transactionDate(LocalDateTime.now())
                .amount(withdrawDto.getAmount())
                .description(withdrawDto.getDescription())
                .transactionType(TransactionType.WITHDRAWAL).build();

        transactionRepository.save(transaction);

        bankAccountService.updateAccountBalance(sourceAcc.getAccountId(), -withdrawDto.getAmount());

        return transaction;
    }

    // function handle deposit
    @Transactional
    @Override
    public Transaction deposit(DepositDto depositDto) throws Exception {
        BankAccount destinationAcc = bankAccountService.getBankAccById(depositDto.getAccountId());

        Transaction transaction = Transaction
                .builder()
                .destinationAccountId(destinationAcc.getAccountId())
                .transactionDate(LocalDateTime.now())
                .amount(depositDto.getAmount())
                .description(depositDto.getDescription())
                .transactionType(TransactionType.DEPOSIT).build();

        transactionRepository.save(transaction);

        bankAccountService.updateAccountBalance(destinationAcc.getAccountId(), depositDto.getAmount());

        return transaction;
    }

    @Override
    public List<Transaction> findAllByMonth(Integer mounth) {
        return transactionRepository.findAllByMonth(mounth);
    }

    @Override
    public List<Transaction> findByIdAccounts(Long id) {
        List<Transaction> optionalTransaction= transactionRepository.findByIdAccounts(id);

        return optionalTransaction;

    }

    @Override

    public List<Transaction> findByIdCustomer(Long id) {
        return transactionRepository.findByUserId(id);
    }

    public List<Transaction> findBySourceAccountId(Long id, Integer mounth) {
        return transactionRepository.findBySourceAccountId(id, mounth);
    }

    public List<Transaction> findByDestinationAccountId(Long id, Integer mounth) {
        return transactionRepository.findByDestinationAccountId(id, mounth);
    }

    public Page<Transaction> getByPage(int page, String sortBy, String sortType, String key){
        Sort sort = Sort.by(sortBy);
        sort= sortType.equals("asc")?sort.ascending(): sort.descending();
        Pageable pageable = PageRequest.of(page-1, 5,sort);
        Page<Transaction> pageRs= transactionRepository.findAll(pageable);
        return pageRs;
    }
}
