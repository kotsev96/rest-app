package com.example.restfultest.exception;

import java.math.BigDecimal;

public class NotMoneyException extends RuntimeException {
    public NotMoneyException(Long fromUser) {
        super("Нет денег на балансе у " + fromUser );
    }
}
