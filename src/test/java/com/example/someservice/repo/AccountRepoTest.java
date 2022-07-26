package com.example.someservice.repo;

import com.example.someservice.entity.UserAccount;
import com.example.someservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
public class AccountRepoTest {

    @Autowired
    private AccountRepository repository;

    private UserAccount testAccount;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void findByNameTest_ShouldPass() {
        //actual
        testAccount = new UserAccount();
        testAccount.setId(2L);
        testAccount.setName("Phill");
        testAccount.setPassword("12345");
        //  testAccount.setPets(Collections.emptySet());

        repository.save(testAccount);

        UserAccount found = repository.findAccountByName("Phill").orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(testAccount.getName());
        assertEquals(found.getPassword(), testAccount.getPassword());

        assertEquals(1, repository.count());

    }

    @Test
    public void test_attempt_login() {
        testAccount = new UserAccount();
        testAccount.setId(2L);
        String name = "Gregg";
        testAccount.setName(name);
        int attempt = 3;
        testAccount.setAttemptLogin(attempt);

        //  testAccount.setPets(Collections.emptySet());

        repository.save(testAccount);


        repository.updateAccountAttempt(attempt, name);

        assertEquals(attempt, testAccount.getAttemptLogin());


    }
}
