package be.jvdd.hibernatedemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries({
        @NamedQuery(name = "Test_findDistinctCustomerNames",
                query = "SELECT DISTINCT account.customerName " +
                        "FROM Account account " +
                        "WHERE UPPER(account.customerName) LIKE CONCAT('%',UPPER(:filter),'%') " +
                        "AND (:accountNames IS NULL OR account.accountName IN (:accountNames)) " +
                        "ORDER BY account.customerName"),
        })
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "customer_name")
    private String customerName;
    private Double amount;
}
