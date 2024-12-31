package com.example.Banking_App.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Banking_App.Entity.TransactionEntity;
import java.util.List;


public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

    List<TransactionEntity> findByAccountIdOrderByTimestampDesc(Long accountId);
}
