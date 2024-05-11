package org.example.transactionservice.kafka;

import lombok.AllArgsConstructor;
import org.example.transactionservice.common.AccountType;
import org.example.transactionservice.model.BankAccount;
import org.example.transactionservice.service.IService.IBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAccountConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(CreateAccountConsumer.class);

    @Qualifier("bankAccountService")
    private IBankAccountService accountService;

    @KafkaListener(
            topics = "create_account",
            groupId = "myGroup"
    )
    public void consumer(String message) {


//        AccountId+" "+CustomerId+" "+AccountType+" "+Balance+" "+enable;
        LOGGER.info(String.format("Event message received => %s", message));

//        customerId+" "+accountId+" "+accountType+" "+balance;

        Long customerId=Long.parseLong(message.split(" ")[0]);
        Long accountId=Long.parseLong(message.split(" ")[1]);
        String accountType= message.split(" ")[2];
        Double balance=Double.parseDouble(message.split(" ")[3]);

        BankAccount bankAccount= new BankAccount();
        bankAccount.setAccountId(accountId);
        bankAccount.setUserId(customerId);
        if (accountType.equals("SAVINGS")){
            bankAccount.setAccountType(AccountType.SAVINGS);
        }else if (accountType.equals("CHECKOUT")){
            bankAccount.setAccountType(AccountType.CHECKOUT);
        }
        bankAccount.setBalance(balance);
        accountService.createAccount(bankAccount);
    }
}
