package com.example.restfultest.exception;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(BigDecimal balance) {
        super("Недостаточно денег на балансе. " + "Баланс " + balance );
    }
}
