package com.example.Banking_App.Dto;

import java.time.LocalDateTime;

public record TransactionDto(Long id,Long accountId,double amount,String transactionType,LocalDateTime timestamp) {
    
}