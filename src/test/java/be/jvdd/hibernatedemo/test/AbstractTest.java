package be.jvdd.hibernatedemo.test;

import be.jvdd.hibernatedemo.HibernateDemoApplication;
import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import be.jvdd.hibernatedemo.service.AccountService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static be.jvdd.hibernatedemo.constants.constants.ACCOUNT_X_X_G;
import static be.jvdd.hibernatedemo.constants.constants.ACCOUNT_X_X_X;
import static be.jvdd.hibernatedemo.constants.constants.ACCOUNT_Y_Y_Y;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = HibernateDemoApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractTest {
    @Inject
    protected AccountRestResource accountRestResource;

    @Inject
    protected AccountService accountService;

    @BeforeEach
    public void setup() {
        createAccount(ACCOUNT_X_X_G);
        createAccount(ACCOUNT_X_X_X);
        createAccount(ACCOUNT_Y_Y_Y);
    }

    public void createAccount(Account account) {
        Account account1 = accountService.createAccount(account);
        accountRestResource.save(account1);
    }
}
