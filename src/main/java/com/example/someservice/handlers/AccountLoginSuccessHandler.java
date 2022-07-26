package com.example.someservice.handlers;

import com.example.someservice.entity.UserAccount;
import com.example.someservice.service.impl.AccountDetail;
import com.example.someservice.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AccountServiceImpl service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        UserAccount account = accountDetail.getUser();
        if (account.getAttemptLogin() > 0) {
            service.resetFailedAttempts(account.getName());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
