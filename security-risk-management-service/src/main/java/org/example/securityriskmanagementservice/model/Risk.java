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
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riskId;

    private String description;

    private String category;

    @ManyToMany(mappedBy = "risks")
    private Set<SecurityMeasure> securityMeasures;
}

