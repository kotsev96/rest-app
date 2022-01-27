package com.example.restfultest.exception;

import java.math.BigDecimal;

public class WrongBalanceAmountException extends RuntimeException {
    public WrongBalanceAmountException(Long id, BigDecimal balance) {
        super("Неверное количество " + balance + " у аккаунта " + id);
    }

    public WrongBalanceAmountException(BigDecimal amount) {
        super("Неверное количество " + amount);
    }
}
