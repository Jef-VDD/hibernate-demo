
package be.jvdd.hibernatedemo.repository;

import be.jvdd.hibernatedemo.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRestResource extends PagingAndSortingRepository<Account, Long>, CrudRepository<Account, Long> {
    @Query("SELECT DISTINCT account.customerName " +
            "FROM Account account " +
            "WHERE UPPER(account.customerName) LIKE CONCAT('%',UPPER(:filter),'%') " +
            "AND (:accountNames IS NULL OR account.accountName IN (:accountNames)) " +
            "ORDER BY account.customerName")
    Page<String> findDistinctCustomerNames(List<String> accountNames, String filter, Pageable page);
}
