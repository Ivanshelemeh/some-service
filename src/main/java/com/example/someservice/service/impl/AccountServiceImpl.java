package com.example.someservice.service.impl;

import com.example.someservice.entity.Role;
import com.example.someservice.entity.UserAccount;
import com.example.someservice.exeption.ResourceNotFoundException;
import com.example.someservice.repository.AccountRepository;
import com.example.someservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    public final static int MAX_ATTEMPTS_LOGIN = 10;
    private static final int TIME_LOCK_DURATION = LocalDateTime.now().plusMinutes(60).getSecond();

    private final AccountRepository accountRepository;

    @Override
    public UserAccount findByAccountName(String userName) {
        Optional<UserAccount> accountOptional = Optional.ofNullable(accountRepository.findAccountByName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("not such account by item username exists")));

        return accountOptional.get();
    }

    @Override
    public boolean exitsAccountByName(String username) {
        if (Optional.ofNullable(accountRepository.findAccountByName(username)).isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public UserAccount createAccount(UserAccount account) {
        Optional<UserAccount> accountOptional = Optional.ofNullable(account);

        if (!accountOptional.isPresent() && !exitsAccountByName(account.getName())) {
            throw new ResourceNotFoundException("account can not create");
        }
        UserAccount userAccount = accountOptional.get();
        userAccount.setPassword(account.getPassword());
        userAccount.setName(account.getName());
        userAccount.setRoles(Collections.singleton(Role.AUTHORIZED));
        return accountRepository.save(userAccount);
    }

    private boolean isUserAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public void increaseAttempts(UserAccount account) {
        int newAttempts = account.getAttemptLogin() + 1;
        accountRepository.updateAccountAttempt(newAttempts, account.getName());
    }

    public void resetFailedAttempts(String name) {
        accountRepository.updateAccountAttempt(0, name);
    }

    public void lock(UserAccount userAccount) {
        userAccount.setAccountNonBlocked(false);
        userAccount.setCreatedAt(userAccount.getCreatedAt());

        accountRepository.save(userAccount);
    }

    public boolean unlockWhenTimeExpired(UserAccount account) {
        int lockTimeInMillis = account.getCreatedAt().getSecond();
        int currentTimeInMillis = Math.toIntExact(System.currentTimeMillis());

        if (lockTimeInMillis + TIME_LOCK_DURATION < currentTimeInMillis) {
            account.setAccountNonBlocked(true);
            account.setAttemptLogin(0);

            accountRepository.save(account);

            return true;
        }

        return false;
    }

}
