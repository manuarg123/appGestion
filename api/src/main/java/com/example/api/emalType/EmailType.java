package com.example.api.emalType;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class EmailType extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 144)
    private String name;

    public EmailType(){}
    public EmailType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmailType(String name) {
        this.name = name;
    }
}
