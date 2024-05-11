package org.example.securityriskmanagementservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Users user;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expirationTime;
}

