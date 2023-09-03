package com.example.api.plan;

import com.example.api.AuditableEntity;
import com.example.api.patient.Patient;
import com.example.api.socialWork.SocialWork;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@RequiredArgsConstructor
@Getter
@Setter
public class Plan extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 144)
    private String name;

    @ManyToOne
    @JoinColumn(name = "social_work_id", nullable = false)
    private SocialWork socialWork;

    @ManyToMany(mappedBy = "plans")
    @JsonBackReference
    private List<Patient> patients;
}
