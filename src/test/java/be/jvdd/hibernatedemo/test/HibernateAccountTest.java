package be.jvdd.hibernatedemo.test;

import be.jvdd.hibernatedemo.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HibernateAccountTest extends AbstractTest {

    //Test fails from time to time...
//    Caused by: org.hibernate.HibernateException: Unknown wrap conversion requested: java.util.ArrayList to java.lang.String : `org.hibernate.type.descriptor.java.StringJavaType` (java.lang.String)
//    at org.hibernate.type.descriptor.java.JavaTypeHelper.unknownWrap(JavaTypeHelper.java:23)
//    at org.hibernate.type.descriptor.java.AbstractClassJavaType.unknownWrap(AbstractClassJavaType.java:115)
//    at org.hibernate.type.descriptor.java.StringJavaType.wrap(StringJavaType.java:103)
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
        accountService.findDistinctCustomerNames(getCurrentSession(), accountNames, null);
        log.info("test done");
    }

    //Can fail on any test execution
    @Test
    public void findAccountWithSingleAccountName_looped() {
        ArrayList<String> accountNames = new ArrayList<>();
        accountNames.add("accountX");
        for (int i = 0; i < 100; i++) {
            log.info("test {}", i);
            accountService.findDistinctCustomerNames(getCurrentSession(), accountNames, null);
        }
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
