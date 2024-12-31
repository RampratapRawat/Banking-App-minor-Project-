package com.example.Banking_App.Mapper;

import com.example.Banking_App.Dto.AccountDto;
import com.example.Banking_App.Entity.Account;

public class AccountMapper {
    
    public static AccountDto mapToAccountDto(Account account){
        AccountDto num=new AccountDto(
            account.getId(),
            account.getAccountHolderName(),
            account.getBalance()
        );
        return num;
    }

    public static Account mapToAccount(AccountDto accountDto){
        Account num=new Account(
            accountDto.getId(),
            accountDto.getAccountHolderName(),
            accountDto.getBalance()
        );
        return num;
    }
}
