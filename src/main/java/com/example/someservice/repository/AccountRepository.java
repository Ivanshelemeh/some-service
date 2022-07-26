package com.example.someservice.repository;

import com.example.someservice.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findAccountByName(String name);

    @Query("UPDATE  UserAccount us set us.attemptLogin= ?1 where us.name= ?2")
    @Modifying
    void updateAccountAttempt(int attemptLogin, String name);
}
