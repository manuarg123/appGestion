package com.example.api.professional;

import com.example.api.address.Address;
import com.example.api.clinicHistory.ClinicHistory;
import com.example.api.email.Email;
import com.example.api.gender.Gender;
import com.example.api.identification.Identification;
import com.example.api.medicalCenter.MedicalCenter;
import com.example.api.phone.Phone;
import com.example.api.realPerson.RealPerson;
import com.example.api.speciality.Speciality;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "professional")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professional extends RealPerson {
    @Column(nullable = true, length = 144)
    private String mp;

    @ManyToMany
    @JoinTable(name = "professional_medical_center",
            joinColumns = @JoinColumn(name = "professional_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_center_id"))
    private List<MedicalCenter> medicalCenters;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;
}
