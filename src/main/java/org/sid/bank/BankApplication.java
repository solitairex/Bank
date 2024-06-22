package org.sid.bank;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.sid.bank.dtos.BankAccountDTO;
import org.sid.bank.dtos.CurrentBankAccountDTO;
import org.sid.bank.dtos.CustomerDTO;
import org.sid.bank.dtos.SavingBankAccountDTO;
import org.sid.bank.entities.*;
import org.sid.bank.enums.AccountStatus;
import org.sid.bank.enums.OperationType;
import org.sid.bank.exceptions.BalanceNotSufficientException;
import org.sid.bank.exceptions.BankAccountNotFoundException;
import org.sid.bank.exceptions.CustomerNotFoundException;
import org.sid.bank.repositories.AccountOperationRepository;
import org.sid.bank.repositories.BankAccountRepository;
import org.sid.bank.repositories.CustomerRepository;
import org.sid.bank.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);

    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Oussame","Zayd","Imrane").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer ->{
                try {

                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.9, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccount:bankAccounts){
                for(int i=0;i<10;i++){
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }else{
                        accountId=((CurrentBankAccountDTO)bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }

            }
        };
    }










    /*CommandLineRunner commandLineRunner(BankService bankService){
        return args-> {bankService.consulter("2ec68e7b-fb03-46a8-9f0d-04861eb7d319");};
    }
    */

//@Bean
    CommandLineRunner start(CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository, BankAccountRepository bankAccountRepository){
return args -> {
    Stream.of("Adam","Yassine","Ammar").forEach(name->{
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(name+"@gmail.com");
        customerRepository.save(customer);
    });
    customerRepository.findAll().forEach(cust->{
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(Math.random()*2);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

        SavingAccount savingAccount =new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(Math.random()*2);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(cust);
        savingAccount.setInterestRate(5.5);
        bankAccountRepository.save(savingAccount);});


    bankAccountRepository.findAll().forEach(acc->{
        for (int i=0;i<5;i++){
            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(Math.random()*12);
            accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
            accountOperation.setBankAccount(acc);
            accountOperationRepository.save(accountOperation);}});




};
    }

}
