package com.BankAHT.accounts.entity;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {

    @Column(name = "created_at",updatable = false)
    private Timestamp createdAt;

    @Column(name = "created_by",updatable = false)
    private String createdBy;

    @Column(name="updated_at",insertable = false)
    private Timestamp  updatedAt;

    @Column(name="updated_by",insertable = false)
    private String updatedBy;
}
