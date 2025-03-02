package com.SimpleBank.services;

import com.SimpleBank.domain.user.User;
import com.SimpleBank.domain.user.UserType;
import com.SimpleBank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {
    @Autowired //Automatic Injection of Dependencies
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception { //validate transaction, check if the user is a common, and check the user balance.
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Merchant type users can't do transactions.");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("User does not have enough balance to make the transaction.");
        }
    }

    public User FindById(Long id) { //find user transactions by id
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public void saveUser(User user) { //saves user balance changes
        this.repository.save(user);
    }
}
