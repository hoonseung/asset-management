package com.sewon.notification.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.sewon.account.model.Account;
import com.sewon.common.converter.BooleanConverter;
import com.sewon.common.model.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE notification SET deleted_at = now() WHERE id = ?")
@FilterDef(name = "notificationDeletedFilter", autoEnabled = true)
@Filter(name = "notificationDeletedFilter", condition = "deleted_at IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "notification")
@Entity
public class Notification extends BaseTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", length = 300, nullable = false)
    private String content;

    @Column(name = "notification_at", nullable = false, updatable = false)
    private LocalDateTime notificationDate;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @ManyToOne(targetEntity = Account.class, fetch = LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    public static Notification of(String content, LocalDateTime notificationDate, Account account) {
        return new Notification(null, content, notificationDate,
            false, account);
    }
}
