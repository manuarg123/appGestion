package com.example.api.medicalCenter;

import com.example.api.person.Person;
import com.example.api.professional.Professional;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="medical_center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCenter extends Person {
    @Column(nullable = false, length = 144)
    private String name;

    @ManyToOne
    @JoinColumn(name="professional_id", nullable = true)
    @JsonBackReference
    private Professional professional;
}