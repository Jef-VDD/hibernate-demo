
package be.jvdd.hibernatedemo.service;

import be.jvdd.hibernatedemo.domain.Account;
import be.jvdd.hibernatedemo.repository.AccountRestResource;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.transaction.Transactional;
import org.hibernate.CacheMode;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.NaturalIdMultiLoadAccess;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionEventListener;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.Transaction;
import org.hibernate.UnknownProfileException;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.graph.RootGraph;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaInsertSelect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.stat.SessionStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
