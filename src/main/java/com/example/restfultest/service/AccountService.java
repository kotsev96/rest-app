package com.example.restfultest.service;

import com.example.restfultest.domain.Account;
import com.example.restfultest.dto.AccountDTO;
import com.example.restfultest.exception.*;
import com.example.restfultest.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository paymentRepository) {
        this.accountRepository = paymentRepository;
    }


    /**
     Сервис для вывода денег со счета
     */
    @Transactional
    public Account doWithdrawal(Account accountMain, Long id) {
        try {
            Account account = accountRepository.getById(id);
            BigDecimal balance = account.getBalance();
            if (accountMain.getBalance().signum() != 1) {
                throw new WrongBalanceAmountException(id, accountMain.getBalance());
            }
            if (balance.subtract(accountMain.getBalance()).signum() == -1) {
                throw new NotEnoughMoneyException(balance);
            }
            BigDecimal newBalance = balance.subtract(accountMain.getBalance());
            account.setBalance(newBalance);
            return accountRepository.save(account);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundAccountException(id);
        }
    }

    /**
     Сервис для вывода денег со счета
     */
    @Transactional
    public Account depositTo(Account accountMain, Long id) {
        try {
            Account account = accountRepository.getById(id);
            BigDecimal balance = account.getBalance();
            if (accountMain.getBalance().signum() != 1) {
                throw new WrongBalanceAmountException(id, accountMain.getBalance());
            }
            BigDecimal newBalance = balance.add(accountMain.getBalance());
            account.setBalance(newBalance);
            return accountRepository.save(account);
        } catch (EntityNotFoundException exception) {
            throw new NotFoundAccountException(id);
        }
    }

    /**
     Сервис для перевода денег с одного счета на другой
     */
    @Transactional
    public List<Account> transferTo(@RequestBody AccountDTO dto) {
        if (dto.getAmount().signum() != 1) {
            throw new WrongBalanceAmountException(dto.getAmount());
        }
        if (dto.getFromUser() == dto.getToUser()) {
            throw new WrongAccountException(dto.getFromUser());
        }
        Account from = accountRepository.findById(dto.getFromUser())
                .orElseThrow(() -> new NotFoundAccountException(dto.getFromUser()));

        Account to = accountRepository.findById(dto.getToUser())
                .orElseThrow(() -> new NotFoundAccountException(dto.getToUser()));
        if (from.getBalance().subtract(dto.getAmount()).signum() == -1) {
            throw new NotMoneyException(dto.getFromUser());
        }

        BigDecimal fromBalance = from.getBalance().subtract(dto.getAmount());
        BigDecimal toBalance = to.getBalance().add(dto.getAmount());
        from.setBalance(fromBalance);
        accountRepository.save(from);
        to.setBalance(toBalance);
        accountRepository.save(to);
        List<Account> accounts = new ArrayList<>();
        accounts.add(from);
        accounts.add(to);

        return accounts;
    }
}


