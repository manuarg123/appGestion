package com.example.api.province;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Province extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String name;

    public Province() {
    }

    public Province(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Province(String name) {
        this.name = name;
    }
}
