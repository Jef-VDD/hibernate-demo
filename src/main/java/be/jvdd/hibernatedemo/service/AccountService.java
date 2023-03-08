
package be.jvdd.hibernatedemo.service;

import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
public class AccountService {

    @Inject
    private AccountRestResource accountRestResource;

    @Transactional
    public Account createAccount(Account account) {
        return accountRestResource.save(account);
    }
}
