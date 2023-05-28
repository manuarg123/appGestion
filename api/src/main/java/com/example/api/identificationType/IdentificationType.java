package com.example.api.identificationType;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class IdentificationType extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    public IdentificationType() {
    }
    public IdentificationType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public IdentificationType(String name) {
        this.name = name;
    }
}
