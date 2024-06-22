package org.sid.bank.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Entity
@AllArgsConstructor@NoArgsConstructor
@DiscriminatorValue("CA")

public class CurrentAccount extends BankAccount {
    private double overDraft;

}
