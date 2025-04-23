package com.sewon.account.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.constant.Role;
import com.sewon.account.converter.RoleConverter;
import com.sewon.affiliation.model.Affiliation;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;


@SQLDelete(sql = "UPDATE account SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "accountDeletedFilter", autoEnabled = true)
@Filter(name = "accountDeletedFilter", condition = "deleted_at IS NULL = :isDeleted")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "account")
@Entity
public class Account extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 350, nullable = false)
    private String password;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne(targetEntity = Affiliation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role")
    private Role role;
}
