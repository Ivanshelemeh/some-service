package com.example.someservice.service.impl;

import com.example.someservice.entity.UserAccount;
import com.example.someservice.repository.AccountRepository;
import com.example.someservice.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository repo;

    @Mock
    private AccountService service;

    @InjectMocks
    private AccountServiceImpl accountService;

    private UserAccount account;

    @BeforeEach
    void setUp() {
        account = new UserAccount();
        account.setName("Petty");
        repo.save(account);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    void findByAccountName() {
        given(repo.findAccountByName(anyString())).willReturn(Optional.of(account));
        UserAccount given = accountService.findByAccountName(account.getName());
        verify(repo, atLeastOnce()).findAccountByName(anyString());
        assertThat(given).isNotNull();

    }


    @Test
    void exitsAccountByName() {
        when(repo.findAccountByName(anyString())).thenReturn(Optional.of(account));
        boolean actualBoolean = accountService.exitsAccountByName("Joddi");
        assertFalse(actualBoolean);
    }

    @Test
    void should_create_a_new_user_account_success() {
        UserAccount account3 = new UserAccount();
        account3.setCreatedAt(LocalDateTime.now().plusMinutes(30));
        account3.setAttemptLogin(null);
        account3.setId(3L);
        account3.setName("Rolo");
        account3.setPassword("12345");
        repo.save(account3);

        assertThat(account3.getName()).isNotNull()
                .isEqualTo("Rolo").
                hasSize(4);
        verify(repo, times(2)).save(any());
    }
}