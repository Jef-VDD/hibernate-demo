package be.jvdd.hibernatedemo.test;

import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import be.jvdd.hibernatedemo.service.AccountService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

import static be.jvdd.hibernatedemo.constants.constants.ACCOUNT_X_X_G;
import static be.jvdd.hibernatedemo.constants.constants.ACCOUNT_X_X_X;
import static be.jvdd.hibernatedemo.constants.constants.ACCOUNT_Y_Y_Y;

@Slf4j
public class SpringAccountTest extends AbstractTest{
    @Inject
    private AccountService accountService;
    @Inject
    protected AccountRestResource accountRestResource;

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

    //Test fails from time to time...
//    Caused by: org.hibernate.HibernateException: Unknown wrap conversion requested: java.util.ArrayList to java.lang.String : `org.hibernate.type.descriptor.java.StringJavaType` (java.lang.String)
//    at org.hibernate.type.descriptor.java.JavaTypeHelper.unknownWrap(JavaTypeHelper.java:23)
//    at org.hibernate.type.descriptor.java.AbstractClassJavaType.unknownWrap(AbstractClassJavaType.java:115)
//    at org.hibernate.type.descriptor.java.StringJavaType.wrap(StringJavaType.java:103)
    @Test
    public void findAccountWithSingleAccountName() {
        ArrayList<String> accountNames = new ArrayList<>();
        accountNames.add("accountX");
        accountRestResource.findDistinctCustomerNames(accountNames, "global", PageRequest.of(0,50));
    }

    //Never fails
    @Test
    public void findAccountWithNoAccountNames() {
        accountRestResource.findDistinctCustomerNames(null, "global", PageRequest.of(0,50));
    }

    //Fails on first execution of the query or runs through all 100 tests
    @Test
    public void findAccountWithSingleAccountName_looped() {
        ArrayList<String> accountNames = new ArrayList<>();
        accountNames.add("accountX");
        for (int i = 0; i < 100; i++) {
            log.info("test {}", i);
            accountRestResource.findDistinctCustomerNames(accountNames, "global", PageRequest.of(0,50));
        }
    }
}
