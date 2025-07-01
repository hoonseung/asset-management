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
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;


@SQLDelete(sql = "UPDATE account SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "accountDeletedFilter", autoEnabled = true)
@Filter(name = "accountDeletedFilter", condition = "deleted_at IS NULL")
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

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 350, nullable = false)
    private String password;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Setter
    @ManyToOne(targetEntity = Affiliation.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "affiliation_id", nullable = false)
    private Affiliation affiliation;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role")
    private Role role;


    public static Account createGeneral(String username, String password, String name) {
        return new Account(null, username, password, name, null, Role.GENERAL);
    }

    public static Account createAdmin(String username, String password, String name) {
        return new Account(null, username, password, name, null, Role.ADMIN);
    }

    public void updateInfo(String name, String username) {
        if (name != null) {
            this.name = name;
        }
        if (username != null) {
            this.username = username;
        }
    }

    public void passwordEncrypting(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
