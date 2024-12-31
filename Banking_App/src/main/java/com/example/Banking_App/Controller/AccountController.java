package com.example.Banking_App.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Banking_App.Dto.AccountDto;
import com.example.Banking_App.Dto.TransactionDto;
import com.example.Banking_App.Dto.TransferFundDto;
import com.example.Banking_App.Service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
//@RequestMapping("/Api/Account")
public class AccountController {
    
    private final AccountService accountservice;
    
    public AccountController(AccountService accountService){
        this.accountservice=accountService;
    }

    @PostMapping("/Create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
            AccountDto saw=accountservice.createAccount(accountDto);
            return new ResponseEntity<>(saw,HttpStatus.CREATED);
    }

    @PutMapping("/debit/{id}")
    public ResponseEntity<AccountDto> minusAccount(@PathVariable Long id, @RequestBody  Map<String,Double> request){
    	  Double amount= request.get("balance");
          AccountDto saw=accountservice.debitBalance(id, amount);
          return ResponseEntity.ok(saw);
    }

    @PutMapping("/credit/{id}")
    public ResponseEntity<AccountDto> plusAccount(@PathVariable Long id, @RequestBody  Map<String,Double> request){
        Double amount= request.get("balance");  
        AccountDto saw=accountservice.creditBalance(id, amount);
          return ResponseEntity.ok(saw);
    }

     @GetMapping("/Details")
         public ResponseEntity<List<AccountDto>> AccountDetails(){
             return ResponseEntity.ok(accountservice.getAllAccount());
         }
     
     @GetMapping("/get/{id}")
     public ResponseEntity<AccountDto> AccountDetails(@PathVariable Long id){
         return ResponseEntity.ok(accountservice.getAccount(id));
     }
     

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        String saw=accountservice.deleteAccount(id);
        return ResponseEntity.ok(saw);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(@RequestBody TransferFundDto entity) {
        accountservice.transferFund(entity);
        return ResponseEntity.ok("Fund Transfer Successfully");
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<List<TransactionDto>> getMethodName(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(accountservice.allTransactions(accountId));
    }
    
    
}
