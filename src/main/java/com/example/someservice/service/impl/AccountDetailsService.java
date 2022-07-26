package com.example.someservice.service.impl;

import com.example.someservice.entity.UserAccount;
import com.example.someservice.exeption.AccountNotFoundException;
import com.example.someservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("AccountDetailsService")
@Slf4j
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private  AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null){
            log.error("not such username"+ username);
            throw new AccountNotFoundException("account not found with such"+ username);
        }
        Optional<UserAccount> optionalUserAccount = accountRepository.findAccountByName(username);
        UserAccount userAccount = optionalUserAccount.get();
        return new AccountDetail(userAccount);

    }
}
