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
package be.ixor.hibernatedemo.service;

import be.ixor.hibernatedemo.domain.Account;
import be.ixor.hibernatedemo.repository.AccountRestResource;
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
