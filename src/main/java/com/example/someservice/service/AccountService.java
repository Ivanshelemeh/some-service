package com.example.someservice.service;

import com.example.someservice.entity.UserAccount;

public interface AccountService {
    /**
     * Method finds specific user's account by
     * username of account
     * @param userName
     * @return
     */
    UserAccount findByAccountName(String userName);

    boolean exitsAccountByName(String username);

    UserAccount createAccount(UserAccount account );
}
