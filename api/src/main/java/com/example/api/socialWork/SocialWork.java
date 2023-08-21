package com.example.api.socialWork;

import com.example.api.patient.Patient;
import com.example.api.person.Person;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="social_work")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialWork extends Person {
    @Column(nullable = false, length = 144)
    private String name;

    @ManyToMany(mappedBy = "socialWorks")
    @JsonBackReference
    private List<Patient> patients;
}
