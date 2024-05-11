package com.aht.UserManagementService.entity;

import com.aht.UserManagementService.Validation.User.EmailNotExists;
import com.aht.UserManagementService.Validation.User.UsernameNotExists;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId"))
    private Set<Role> roles;
}
