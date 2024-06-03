package com.BankAHT.accounts.controller;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.dto.MessageUpdateAccount;
import com.BankAHT.accounts.dto.UserDTO;
import com.BankAHT.accounts.entity.Accounts;
import com.BankAHT.accounts.service.IAccountService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/account")
@Validated
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAccount(@RequestParam Long accountId){
        AccountDto accountDto= accountService.fetchAccount(accountId);

        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
    }

    @GetMapping("/ad")
    public List<AccountDto> getAllAccount() {
        return accountService.getAllAccount();
    }

    @GetMapping("/accountInfo")
    public List<AccountDto> getAllAccountByUserId(@RequestParam Long userId) {
        return accountService.getAllAccountByUserId(userId);
    }

    @GetMapping("/month")
    public List<AccountDto> getAllAccountByMonth(@RequestParam Integer month) {
        return accountService.getAllAccountByMonth(month);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto, @RequestHeader("Authorization") String token){
        Long userId = accountDto.getCustomerId();
        String url = "http://localhost:8081/api/v1/user/" + userId;

        // Tạo HttpHeaders và đính kèm token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); // Đặt token vào header từ request ban đầu

        // Tạo HttpEntity với headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Sử dụng RestTemplate để gọi API
        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            accountService.createAccount(accountDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountDto);
        }
    }

    @PostMapping("/ad/create")
    public ResponseEntity<?> createAccountAdmin(@RequestBody AccountDto accountDto, @RequestHeader("Authorization") String token){
//        Long userId = accountDto.getCustomerId();
//        String url = "http://localhost:8081/api/v1/user/" + userId;
//
//        // Tạo HttpHeaders và đính kèm token
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token); // Đặt token vào header từ request ban đầu
//
//        // Tạo HttpEntity với headers
//        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Sử dụng RestTemplate để gọi API
//        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class);
//
//        if(response.getStatusCode() == HttpStatus.OK) {
//            accountService.createAccount(accountDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountDto);
//        }

        Accounts accounts=accountService.createAccount(accountDto);
        accountService.enableAccount(accounts.getAccountId());
        return ResponseEntity.status(HttpStatus.OK).body("Create account Success !");
    }

    @GetMapping("/ad/account-Creation-Statistics")
    public ResponseEntity<?> getAccountCreationStatistics( @RequestHeader("Authorization") String token){

        return ResponseEntity.status(HttpStatus.OK).body(accountService.accountCreationStatistics());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDto accountDto){
        accountService.updateAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestParam @Min(value = 0, message = "AccountNumber phải là một số không âm")
                                               @Max(value = 9999999999L, message = "AccountNumber không được vượt quá 10 chữ số")
                                               Long accountId){
        accountService.deleteAccount(accountId);
        MessageUpdateAccount messageUpdateAccount= new MessageUpdateAccount();
        messageUpdateAccount.setAccountId(accountId);
        messageUpdateAccount.setEnable(false);
        accountService.producerMessageUpdateAccountTransaction(messageUpdateAccount);
        return ResponseEntity.status(HttpStatus.OK).body("Delete Success !");
    }

    @PostMapping("/ad/enable")
    public ResponseEntity<?> enableAccount(@RequestParam @Min(value = 0, message = "AccountNumber phải là một số không âm")
                                           @Max(value = 9999999999999999L, message = "AccountNumber không được vượt quá 16 chữ số")
                                           Long accountId){
        accountService.enableAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).body("Enable Account "+accountId+" Success !");
    }

    @GetMapping("/user_infor/{accountId}")
    public UserDTO getUserByAccountId(@PathVariable(name = "accountId") Long accountId, @RequestHeader("Authorization") String token) {
        Long userId = accountService.getUserIdByAccountId(accountId);
        String url = "http://localhost:8081/api/v1/user/" + userId;

        // Tạo HttpHeaders và đính kèm token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); // Đặt token vào header từ request ban đầu

        // Tạo HttpEntity với headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Sử dụng RestTemplate để gọi API
        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class);

        // Trả về UserDTO từ response
        return response.getBody();
    }

//    @PutMapping("/activeAccount")
//    public ResponseEntity<?> enableAccount(@RequestBody AccountDto accountDto){
//        accountService.activeAccount(accountDto.getAccountId());
//        return ResponseEntity.status(HttpStatus.OK).body(accountDto);
//    }

}
