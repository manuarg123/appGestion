package com.example.api.identification;

import com.example.api.AuditableEntity;
import com.example.api.identificationType.IdentificationType;
import com.example.api.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Identification extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String number;

    @ManyToOne
    @JoinColumn(name="type_id",nullable = false)
    private IdentificationType type;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    private Person person;

    public Identification() {
    }

    public Identification(Long id, String number, IdentificationType type, Person person) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.person = person;
    }

    public Identification(String number, IdentificationType type, Person person) {
        this.number = number;
        this.type = type;
        this.person = person;
    }
}
