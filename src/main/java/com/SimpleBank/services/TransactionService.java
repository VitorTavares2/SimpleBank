package com.SimpleBank.services;

import com.SimpleBank.domain.transaction.Transaction;
import com.SimpleBank.domain.user.User;
import com.SimpleBank.dtos.TransactionDTO;
import com.SimpleBank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService; //will receive all users info

    @Autowired
    private TransactionRepository repository; //will receive all transactions info

    @Autowired
    private RestTemplate restTemplate; // Inject RestTemplate bean

    public void createTransaction(TransactionDTO transaction) throws Exception{
        User sender = this.userService.FindById(transaction.senderId()); //set the transaction according to the sender and the receiver id.
        User receiver = this.userService.FindById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value()); //check if the transaction value is authorized by the sender balance
    boolean isAuthorized = authorizeTransaction(sender, transaction.value());

        if(!isAuthorized){ // check if the authorization its true or not, if its release the rest of code.
            throw new Exception("Transaction not authorized.");
        }

    Transaction newTransaction = new Transaction(); //create a new transaction and add her details
    newTransaction.setAmount(transaction.value()); //set the transaction value
    newTransaction.setSender(sender); //set the transaction sender
    newTransaction.setReceiver(receiver); //set the transaction receiver
    newTransaction.setTimestamp(LocalDateTime.now()); //set the transaction time.

    sender.setBalance(sender.getBalance().subtract(transaction.value())); //reload the sender balance
    receiver.setBalance(receiver.getBalance().add(transaction.value())); //reload the receiver balance

    this.repository.save(newTransaction);//save the transaction infos on the database
    this.userService.saveUser(sender);//save the sender infos on the database
    this.userService.saveUser(receiver);//save the receiver infos on the database.
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) { //checks on the microservice if the transactions is authorized
       ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
       if(authorizationResponse.getStatusCode() == HttpStatus.OK){
           String message = (String) authorizationResponse.getBody().get("message");
           return "Autorizado".equalsIgnoreCase(message);
       } else return false;

    }
}
