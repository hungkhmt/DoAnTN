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
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Users user;

    private String activityType;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
}

