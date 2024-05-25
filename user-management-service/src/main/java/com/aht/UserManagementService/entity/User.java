package com.aht.UserManagementService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    @Column(name = "userId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "fullname", length = 50, nullable = false)
    private String fullname;

    @Column(name = "dateOfBirth", length = 50, nullable = false)
    private String dateOfBirth;

    @Column(name = "phoneNumber", length = 20, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId"))
    private Set<Role> roles;
}
