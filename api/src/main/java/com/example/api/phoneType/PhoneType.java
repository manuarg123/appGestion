package com.example.api.phoneType;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class PhoneType extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 144)
    private String name;

    public PhoneType(){
    }

    public PhoneType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PhoneType(String name) {
        this.name = name;
    }
}
