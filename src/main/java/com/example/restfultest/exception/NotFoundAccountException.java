package com.example.restfultest.exception;

public class NotFoundAccountException extends RuntimeException {
    public NotFoundAccountException(Long id) {
        super("Не найден аккаунт " + id);
    }
}
