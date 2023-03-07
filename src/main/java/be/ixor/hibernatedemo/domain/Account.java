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
package be.ixor.hibernatedemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String accountName;

    private String supplierName;

    private String customerName;

    private Double amount;
}
