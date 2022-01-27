package com.example.restfultest.controller;

import com.example.restfultest.domain.Account;
import com.example.restfultest.domain.User;
import com.example.restfultest.repository.AccountRepository;
import com.example.restfultest.repository.UserRepository;
import com.example.restfultest.service.AccountService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Написал простенькие тесты на проверку 200 статуса
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getOne() throws Exception {
        when(accountRepository
                .findById(anyLong()))
                .thenReturn(Optional.of(new Account(1L, new BigDecimal("1000.0"))));
        mockMvc.perform(get("/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.balance").exists());
    }

    @Test
    void depositTo() throws Exception {
        String jsonString = "{\"balance\": \"500\"}";
        mockMvc.perform(put("/depositto/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());


    }

    @Test
    void transferTo() throws Exception {
        String jsonString = "{\n" +
                "    \"fromUser\": \"1\", \"toUser\":\"2\", \"amount\": \"100\"\n" +
                "}";
        mockMvc.perform(post("/transferto/")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    void withdrawal() throws Exception {
        String jsonString = "{\"balance\": \"500\"}";
        mockMvc.perform(put("/withdrawal/1")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }
}