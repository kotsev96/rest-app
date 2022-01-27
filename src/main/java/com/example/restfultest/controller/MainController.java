package com.example.restfultest.controller;

import com.example.restfultest.domain.Account;
import com.example.restfultest.dto.AccountDTO;
import com.example.restfultest.exception.NotFoundAccountException;
import com.example.restfultest.exception.WrongBalanceAmountException;
import com.example.restfultest.repository.AccountRepository;
import com.example.restfultest.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Api("Главный контроллер для перевода, ввода и вывода денег.")
public class MainController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Autowired
    public MainController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    /**
     Контроллер для показа баланса пользователя
     */
    @GetMapping("/account/{id}")
    @ApiOperation("Показать счет юзера")
    Account getOne(@PathVariable Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundAccountException(id));
    }

    /**
     Контроллер ввода денег на счет пользователя
     */
    @PutMapping("/depositto/{id}")
    @ApiOperation("Зачисление денег на счет")
    public Account depositTo(@RequestBody Account accountMain, @PathVariable Long id) {
        return accountService.depositTo(accountMain, id);
    }

    /**
     Контроллер для перевода денег с одного счета на другой
     */
    @PostMapping("/transferto/")
    @ApiOperation("Перевод денег с одного счета на другой")
    public List<Account> transferTo(@RequestBody AccountDTO dto) {
        return accountService.transferTo(dto);
    }

    /**
     Контроллер для вывода денег со счета
     */
    @PutMapping("withdrawal/{id}")
    @ApiOperation("Вывод денег со счета")
    public Account withdrawal(@RequestBody Account accountMain, @PathVariable Long id) {
        return accountService.doWithdrawal(accountMain, id);
    }




}
