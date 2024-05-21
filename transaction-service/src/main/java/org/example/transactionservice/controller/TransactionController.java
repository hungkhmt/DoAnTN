package org.example.transactionservice.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.transactionservice.dto.transaction.DepositDto;
import org.example.transactionservice.dto.transaction.TransferDto;
import org.example.transactionservice.dto.transaction.WithdrawDto;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.service.implement.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
        try {
            return ResponseEntity.ok(transactionService.transfer(transferDto));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawDto withdrawDto) {
        try {
            return ResponseEntity.ok(transactionService.withdraw(withdrawDto));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositDto depositDto) {
        try {
            return ResponseEntity.ok(transactionService.deposit(depositDto));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/listAllTransaction")
    public ResponseEntity<?> listAllTransaction() {
        List<Transaction> listTransaction= transactionService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }
    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<?> transactionByPage(@PathVariable("pageNumber") int pageNumber) {
        Page<Transaction> listTransaction= transactionService.getByPage(pageNumber,"transactionId","asc",null);
        return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listAllTransactionById() {
        List<Transaction> listTransaction= transactionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listTransaction);
    }

}
