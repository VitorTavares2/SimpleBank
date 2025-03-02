package com.SimpleBank.repositories;

import com.SimpleBank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
