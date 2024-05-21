package com.BankAHT.accounts.controller;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.dto.MessageUpdateAccount;
import com.BankAHT.accounts.dto.UserDTO;
import com.BankAHT.accounts.service.IAccountService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/account")
@Validated
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAccount(@RequestParam @Min(value = 0, message = "AccountNumber phải là một số không âm")
                                              @Max(value = 9999999999L, message = "AccountNumber không được vượt quá 10 chữ số")
                                              Long accountId){
        AccountDto accountDto= accountService.fetchAccount(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto){
        Long userId = accountDto.getCustomerId();
        String url = "http://localhost:8081/api/user/" + userId;
        UserDTO response = restTemplate.getForObject(url, UserDTO.class);
        if(response != null) {
            accountService.createAccount(accountDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountDto);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDto accountDto){
        accountService.updateAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> updateAccount(@RequestParam @Min(value = 0, message = "AccountNumber phải là một số không âm")
                                               @Max(value = 9999999999L, message = "AccountNumber không được vượt quá 10 chữ số")
                                               Long accountId){
        accountService.deleteAccount(accountId);
        MessageUpdateAccount messageUpdateAccount= new MessageUpdateAccount();
        messageUpdateAccount.setAccountId(accountId);
        messageUpdateAccount.setEnable(false);
        accountService.producerMessageUpdateAccountTransaction(messageUpdateAccount);
        return ResponseEntity.status(HttpStatus.OK).body("Delete Success !");
    }

    @PostMapping("/enable")
    public ResponseEntity<?> enableAccount(@RequestParam @Min(value = 0, message = "AccountNumber phải là một số không âm")
                                           @Max(value = 9999999999L, message = "AccountNumber không được vượt quá 10 chữ số")
                                           Long accountId){
        accountService.enableAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).body("Enable Account "+accountId+" Success !");
    }

    @GetMapping("/user_infor/{accountId}")
    public UserDTO getUserByAccountId(@PathVariable(name = "accountId") Long accountId) {
        Long userId = accountService.getUserIdByAccountId(accountId);
        String url = "http://localhost:8081/api/user/" + userId;
        UserDTO response = restTemplate.getForObject(url, UserDTO.class);
        return response;
    }

}
