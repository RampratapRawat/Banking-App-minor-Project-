package com.example.Banking_App.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banking_App.Dto.AccountDto;
import com.example.Banking_App.Dto.TransactionDto;
import com.example.Banking_App.Dto.TransferFundDto;
import com.example.Banking_App.Entity.Account;
import com.example.Banking_App.Entity.TransactionEntity;
import com.example.Banking_App.Exception.AccountException;
import com.example.Banking_App.Mapper.AccountMapper;
import com.example.Banking_App.Repository.AccountRespository;
import com.example.Banking_App.Repository.TransactionRepository;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRespository accountRespository;
  private final TransactionRepository transactionRepository;

  public AccountServiceImpl(AccountRespository accountRespository,TransactionRepository transactionRepository) {
    this.accountRespository = accountRespository;
    this.transactionRepository=transactionRepository;
  }

  public static TransactionDto mapToTransactionDto(TransactionEntity entity){
         TransactionDto ent=new TransactionDto(
                  entity.getId(),
                  entity.getAccountId(),
                  entity.getAmount(),
                  entity.getTransactionType(),
                  entity.getTimestamp()
         );
         return ent;
  }

  @Override
  public AccountDto createAccount(AccountDto accountDto) {
    Account num = AccountMapper.mapToAccount(accountDto);
    return AccountMapper.mapToAccountDto(accountRespository.save(num));
  }

  @Override
  public AccountDto debitBalance(Long id, double balance) {
    Account num = accountRespository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
    if (num.getBalance() < balance) {
      throw new RuntimeException("Balance is low");
    }
    double arr = num.getBalance() - balance;
    num.setBalance(arr);
     Account ram=accountRespository.save(num);
    TransactionEntity transaction=new TransactionEntity();
              transaction.setAccountId(id);
              transaction.setAmount(balance);
              transaction.setTransactionType("DEBIT");
              transaction.setTimestamp(LocalDateTime.now());
      transactionRepository.save(transaction); 

    return AccountMapper.mapToAccountDto(ram);

           
  }

  @Override
  public AccountDto creditBalance(Long id, double balance) {
    Account num = accountRespository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));

    double arr = num.getBalance() + balance;
    num.setBalance(arr);
    Account ram=accountRespository.save(num);
    TransactionEntity transaction=new TransactionEntity();
              transaction.setAccountId(id);
              transaction.setAmount(balance);
              transaction.setTransactionType("CREDIT");
              transaction.setTimestamp(LocalDateTime.now());
      transactionRepository.save(transaction); 
    return AccountMapper.mapToAccountDto(ram);
  }

  @Override
  public List<AccountDto> getAllAccount() {
    List<Account> num = accountRespository.findAll();
    return num.stream().map(e -> AccountMapper.mapToAccountDto(e)).collect(Collectors.toList());
  }

  @Override
  public String deleteAccount(Long id) {
    Account num = accountRespository.findById(id).orElse(null);
    if (num != null) {
      accountRespository.deleteById(id);
      return "Account has been deleted";
    } else {
      return "Account does not existed";
    }

  }

  @Override
  public AccountDto getAccount(long id) {
    Account num = accountRespository.findById(id).orElseThrow(() -> new AccountException("Account does not found"));
    return AccountMapper.mapToAccountDto(num);
  }

  @Override
  public void transferFund(TransferFundDto transfer) {

    Account fromAccount = accountRespository.findById(transfer.fromAccountId())
        .orElseThrow(() -> new AccountException("FromAccountId account does not found"));
    Account toAccount = accountRespository.findById(transfer.toAccountId())
        .orElseThrow(() -> new AccountException("ToAccountId Account does not found"));
     if(fromAccount.getBalance() < transfer.amount()){
         throw new RuntimeException("Insufficient Amount");
     }
    fromAccount.setBalance(fromAccount.getBalance() - transfer.amount());
    toAccount.setBalance(toAccount.getBalance() + transfer.amount()); 

    accountRespository.save(fromAccount);
    accountRespository.save(toAccount);

    
    TransactionEntity transaction=new TransactionEntity();
              transaction.setAccountId(transfer.fromAccountId());
              transaction.setAmount(transfer.amount());
              transaction.setTransactionType("Transfer");
              transaction.setTimestamp(LocalDateTime.now());
      transactionRepository.save(transaction);

  }

  @Override
  public List<TransactionDto> allTransactions(Long accountId) {
    List<TransactionEntity> tree=transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
    return tree.stream().map(e->mapToTransactionDto(e)).collect(Collectors.toList());
 }

}
