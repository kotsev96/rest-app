package com.example.restfultest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    private BigDecimal amount;
    private Long fromUser;
    private Long toUser;
}
