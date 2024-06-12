package org.example.transactionservice.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactionservice.dto.transaction.*;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.service.implement.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@AllArgsConstructor
@Slf4j
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) throws Exception {
        try {
            Transaction tranfer = transactionService.transfer(transferDto);
            return ResponseEntity.status(HttpStatus.OK).body(tranfer);
        } catch (Exception exception) {
            log.info("FAIL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto withdrawDto) {
        try {
            Transaction withdraw = transactionService.withdraw(withdrawDto);
            return ResponseEntity.status(HttpStatus.OK).body(withdraw);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositDto depositDto) {
        try {
            Transaction deposit = transactionService.deposit(depositDto);
            return ResponseEntity.status(HttpStatus.OK).body(deposit);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/listAllTransaction")
    public ResponseEntity<?> listAllTransaction(@RequestParam Integer month) {
        List<Transaction> listTransaction= transactionService.findAllByMonth(month);
            return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

    @GetMapping("/total")
    public ApiResponse<Integer> getTotalTransaction() {
        List<Transaction> transactionList = transactionService.findAllTransaction();
        return ApiResponse.<Integer>builder().result(transactionList.size()).build();
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<?> transactionByPage(@PathVariable("pageNumber") int pageNumber) {
        Page<Transaction> listTransaction= transactionService.getByPage(pageNumber,"transactionId","asc",null);
        return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

    @GetMapping("/transaction-by-accountId/{id}")
    public ResponseEntity<?> listAllTransactionByAccountId(@PathVariable("id") Long idAccount,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "4") int limit) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("transactionId").ascending()
        );

        Page<Transaction> pageTransaction= transactionService.findByIdAccounts(idAccount, pageRequest);

        int totalPage = pageTransaction.getTotalPages();

        List<Transaction> listTransaction = pageTransaction.getContent();
        ListPageResponse listPageResponse = ListPageResponse.builder().transactionList(listTransaction).totalPage(totalPage).build();
        return ResponseEntity.status(HttpStatus.OK).body(listPageResponse);
    }

    @GetMapping("/sourceId/{id}")
    public ResponseEntity<?> listAllTransactionBySourceAccId(@PathVariable("id") Long idAccount, @RequestParam(required = false) Integer mounth) {

        List<Transaction> listTransaction= transactionService.findBySourceAccountId(idAccount, mounth);
        return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

    @GetMapping("/destinationId/{id}")
    public ResponseEntity<?> listAllTransactionByDestinationAccId(@PathVariable("id") Long idAccount, @RequestParam(required = false) Integer mounth) {
        List<Transaction> listTransaction= transactionService.findByDestinationAccountId(idAccount, mounth);
        return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

    @GetMapping("/transaction-by-customerId/{id}")
    public ResponseEntity<?> listAllTransactionByCustomerId(@PathVariable("id") Long idCustomer) {
        List<Transaction> listTransaction= transactionService.findByIdCustomer(idCustomer);
        return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

}
