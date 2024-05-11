package org.example.securityriskmanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measureId;

    private String description;

    private String category;

    @ManyToMany
    @JoinTable(
            name = "security_measure_risk",
            joinColumns = @JoinColumn(name = "measure_id"),
            inverseJoinColumns = @JoinColumn(name = "risk_id")
    )
    private Set<Risk> risks;
}
