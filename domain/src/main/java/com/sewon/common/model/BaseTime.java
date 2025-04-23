package com.sewon.common.model;

import static java.time.LocalDateTime.now;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTime {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedDate;

    @Column(name = "deleted_at", updatable = false)
    protected LocalDateTime deletedDate;


    protected void setDeletedAt() {
        this.deletedDate = now();
    }

    protected boolean isDeleted() {
        return deletedDate != null;
    }
}
