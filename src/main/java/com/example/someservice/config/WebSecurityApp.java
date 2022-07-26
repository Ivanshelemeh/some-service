package com.example.someservice.config;

import com.example.someservice.handlers.AccountLoginFailHandler;
import com.example.someservice.handlers.AccountLoginSuccessHandler;
import com.example.someservice.repository.AccountRepository;
import com.example.someservice.service.impl.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityApp extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountLoginFailHandler accountLoginFailHandler;

    @Autowired
    private AccountLoginSuccessHandler successHandler;

    public UserDetailsService userDetailsService() {
        return new AccountDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**")
                .antMatchers("/templates/**")
                .antMatchers("/static/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/pet/**").fullyAuthenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("name")
                .failureHandler(accountLoginFailHandler)
                .successHandler(successHandler)
                .and()
                .httpBasic();
    }

}
