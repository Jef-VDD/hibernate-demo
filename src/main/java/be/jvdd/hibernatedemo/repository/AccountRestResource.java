/*
 * 2020 (c) Ixor BV
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Ixor BV
 *
 * The intellectual and technical concepts contained
 * herein are proprietary to Ixor BV
 * and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Ixor BV.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.
 */
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

    //TODO
    @Query("SELECT DISTINCT account.customerName " +
            "FROM Account account " +
            "WHERE UPPER(account.customerName) LIKE CONCAT('%',UPPER(:filter),'%') " +
            "AND ((:accountNames) IS NULL OR account.accountName IN (:accountNames)) " +
            "ORDER BY account.customerName")
    Page<String> findDistinctCustomerNames(List<String> accountNames, String filter, Pageable page);
}
