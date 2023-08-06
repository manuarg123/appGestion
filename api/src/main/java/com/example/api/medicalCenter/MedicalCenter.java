package com.example.api.medicalCenter;

import com.example.api.person.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}