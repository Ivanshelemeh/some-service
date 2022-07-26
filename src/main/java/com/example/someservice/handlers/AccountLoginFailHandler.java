package com.example.someservice.handlers;

import com.example.someservice.entity.UserAccount;
import com.example.someservice.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccountLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {


    private final AccountServiceImpl accountService;

    @Autowired
    public AccountLoginFailHandler(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String nameAccount = request.getParameter("name");
        UserAccount userAccount = accountService.findByAccountName(nameAccount);
        if (userAccount != null) {
            if (userAccount.getAttemptLogin() < AccountServiceImpl.MAX_ATTEMPTS_LOGIN - 1) {
                accountService.increaseAttempts(userAccount);
            } else {
                accountService.lock(userAccount);
                exception = new LockedException("Your account has been locked after bad attempts to login ");
            }
        } else if (!userAccount.isAccountNonBlocked()) {
            if (accountService.unlockWhenTimeExpired(userAccount)) {
                exception = new LockedException("Your account has been unlocked. Try to login ...");
            }

        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
