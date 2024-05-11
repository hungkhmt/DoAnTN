package com.aht.UserManagementService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private static final long serialVersionUID = 1L;

    @Column(name = "roleId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(name = "roleName")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public enum RoleName {
        ADMIN, USER
    }
}
