
package be.jvdd.hibernatedemo.service;

import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Named
public class AccountService {
    @Inject
    private AccountRestResource accountRestResource;

    @Transactional
    public Account createAccount(Account account) {
        return accountRestResource.save(account);
    }

    public List<String> findDistinctCustomerNames(Session session, List<String> accountNames, String filter) {
        session.beginTransaction();
        Query<String> findDistinctCustomerNames = session.createNamedQuery("Test_findDistinctCustomerNames", String.class);
        findDistinctCustomerNames.setParameter("accountNames", accountNames);
        findDistinctCustomerNames.setParameter("filter", filter);
        return findDistinctCustomerNames.getResultList();
    }
}
