package be.jvdd.hibernatedemo.test;

import be.jvdd.hibernatedemo.HibernateDemoApplication;
import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import be.jvdd.hibernatedemo.service.AccountService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = HibernateDemoApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class AccountTest {

    public static final String ACCOUNT_NAME_X = "accountX";
    public static final String SUPPLIER_ACCOUNT_X = "supplierAccountX";
    public static final String GLOBAL_CUSTOMER = "globalCustomer";
    public static final String CUSTOMER_X = "customerX";
    public static final String ACCOUNT_Y = "accountY";
    public static final String SUPPLIER_ACCOUNT_Y = "supplierAccountY";
    public static final String CUSTOMER_Y = "customerY";

    public static final Account ACCOUNT_X_X_G = Account.builder()
            .accountName(ACCOUNT_NAME_X)
            .supplierName(SUPPLIER_ACCOUNT_X)
            .customerName(GLOBAL_CUSTOMER)
            .amount(10.0)
            .build();

    public static final Account ACCOUNT_X_X_X = Account.builder()
            .accountName(ACCOUNT_NAME_X)
            .supplierName(SUPPLIER_ACCOUNT_X)
            .customerName(CUSTOMER_X)
            .amount(20.0)
            .build();

    public static final Account ACCOUNT_Y_Y_Y = Account.builder()
            .accountName(ACCOUNT_Y)
            .supplierName(SUPPLIER_ACCOUNT_Y)
            .customerName(CUSTOMER_Y)
            .amount(30.0)
            .build();
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
}
