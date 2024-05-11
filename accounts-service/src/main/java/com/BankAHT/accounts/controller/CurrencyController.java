package com.BankAHT.accounts.controller;

import com.BankAHT.accounts.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @Autowired
    private ICurrencyService currencyService;

    @GetMapping("/fetch")
    public ResponseEntity<?> getCurrency(@RequestParam String currencyCode){
        return ResponseEntity.ok().body(currencyService.getCurrency(currencyCode));
    }
}
