package com.example.Banking_App.Dto;

public record TransferFundDto(Long fromAccountId, Long toAccountId, double amount) {

}
