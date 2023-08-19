package com.example.api.medicalCenter;

import com.example.api.person.Person;
import com.example.api.professional.Professional;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="medical_center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCenter extends Person {
    @Column(nullable = false, length = 144)
    private String name;

    @ManyToMany(mappedBy = "medicalCenters")
    @JsonBackReference
    private List<Professional> professionals;
}