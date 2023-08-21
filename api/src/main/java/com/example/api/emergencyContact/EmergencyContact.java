package com.example.api.emergencyContact;

import com.example.api.AuditableEntity;
import com.example.api.address.Address;
import com.example.api.email.Email;
import com.example.api.patient.Patient;
import com.example.api.person.Person;
import com.example.api.phone.Phone;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="emergency_contact")
@Getter
@Setter
public class EmergencyContact extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 144)
    private String name;

    @Column(name="phone_number",nullable = false, length = 144)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    @JsonBackReference
    private Patient patient;
}
