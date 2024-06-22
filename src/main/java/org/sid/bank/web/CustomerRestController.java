package org.sid.bank.web;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.bank.dtos.CustomerDTO;
import org.sid.bank.entities.Customer;
import org.sid.bank.exceptions.CustomerNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.sid.bank.services.BankAccountService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchcustomers(@RequestParam(name="keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomer("%"+keyword+"%");
    }
    
    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name="id")Long customerId)throws CustomerNotFoundException {
return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable long id){bankAccountService.deleteCustomer(id);}
}
