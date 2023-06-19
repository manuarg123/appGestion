package com.example.api.email;

import com.example.api.AuditableEntity;
import com.example.api.emalType.EmailType;
import com.example.api.person.Person;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Email extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 144)
    private String value;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    @JsonBackReference
    private Person person;

    @ManyToOne
    @JoinColumn(name="type_id", nullable = false)
    private EmailType type;

    public Email() {
    }

    public Email(Long id, String value, Person person, EmailType type) {
        this.id = id;
        this.value = value;
        this.person = person;
        this.type = type;
    }

    public Email(String value, Person person, EmailType type) {
        this.value = value;
        this.person = person;
        this.type = type;
    }
}
