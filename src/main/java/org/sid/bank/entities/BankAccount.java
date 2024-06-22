package org.sid.bank.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bank.enums.AccountStatus;

import java.util.*;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length=4)
@DiscriminatorValue("BA")
@Data@AllArgsConstructor@NoArgsConstructor

public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy ="bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;

}
