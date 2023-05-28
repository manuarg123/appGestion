package com.example.api.speciality;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Speciality extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 144)
    private String name;

    public Speciality() {
    }

    public Speciality(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Speciality(String name) {
        this.name = name;
    }
}
