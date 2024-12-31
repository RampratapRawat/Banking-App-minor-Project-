package com.example.Banking_App.Service;

import java.util.List;

import com.example.Banking_App.Dto.AccountDto;
import com.example.Banking_App.Dto.TransactionDto;
import com.example.Banking_App.Dto.TransferFundDto;

public interface AccountService {

  AccountDto createAccount(AccountDto accountDto);

  AccountDto debitBalance(Long id, double balance);

  AccountDto creditBalance(Long id, double balance);

  List<AccountDto> getAllAccount();

  String deleteAccount(Long id);

  AccountDto getAccount(long id);

  void transferFund(TransferFundDto transfer);

  List<TransactionDto> allTransactions(Long accountId);

}
