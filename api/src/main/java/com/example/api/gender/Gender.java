package com.example.api.gender;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Gender extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    public Gender() {
    }

    public Gender(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Gender(String name) {
        this.name = name;
    }
}
