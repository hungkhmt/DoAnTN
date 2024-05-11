package org.example.transactionservice.exception;

public class AccountIsNotValidException extends RuntimeException{

    public AccountIsNotValidException(String message) {
        super(message);
    }
}
