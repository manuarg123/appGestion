package com.example.api.phone;

import com.example.api.AuditableEntity;
import com.example.api.person.Person;
import com.example.api.phoneType.PhoneType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Phone extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 45)
    private String number;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name="type_id", nullable = false)
    private PhoneType type;

    private Phone(){}

    public Phone(Long id, String number, Person person, PhoneType type) {
        this.id = id;
        this.number = number;
        this.person = person;
        this.type = type;
    }

    public Phone(String number, Person person, PhoneType type) {
        this.number = number;
        this.person = person;
        this.type = type;
    }
}
