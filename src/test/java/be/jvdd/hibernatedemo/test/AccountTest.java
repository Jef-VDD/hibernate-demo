package be.jvdd.hibernatedemo.test;

import be.jvdd.hibernatedemo.HibernateDemoApplication;
import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import be.jvdd.hibernatedemo.service.AccountService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void findCustumerWithHibernate() {
        ArrayList<String> accountNames = new ArrayList<>();
        accountNames.add("accountX");
        List<String> customerNames = accountService.findDistinctCustomerNames(getCurrentSession(), accountNames, "global");

        log.info("test done");
    }

    @Test
    public void findCustumerWithHibernate_noFilter() {
        ArrayList<String> accountNames = new ArrayList<>();
        accountNames.add("accountX");
        List<String> customerNames = accountService.findDistinctCustomerNames(getCurrentSession(), accountNames, null);

        log.info("test done");
    }


    public static Session getCurrentSession() {
        Map<String, Object> settings = new HashMap<>();
        settings.put("connection.driver_class", "org.postgresql.Driver");
        settings.put("dialect", "org.hibernate.dialect.PostgreSQLDialec");
        settings.put("hibernate.connection.url", "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;Mode=PostgreSQL");
        settings.put("hibernate.connection.username", "sa");
        settings.put("hibernate.connection.password", "");
        settings.put("hibernate.current_session_context_class", "thread");
//        settings.put("hibernate.show_sql", "true");
//        settings.put("hibernate.format_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Account.class);
        Metadata metadata = metadataSources.buildMetadata();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.getCurrentSession();
        return session;
    }
}
