package com.example.restfultest.exception;

public class WrongAccountException extends RuntimeException {
    public WrongAccountException(Long fromUser) {
        super("Неверный аккаунт " + fromUser);
    }
}
