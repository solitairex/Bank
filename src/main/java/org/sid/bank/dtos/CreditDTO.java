package org.sid.bank.dtos;

import lombok.Data;
import lombok.Getter;

@Data
public class CreditDTO {
    private String accountId;
    private double amount;
    private String description;
}
