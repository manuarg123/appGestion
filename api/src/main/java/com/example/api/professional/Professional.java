package com.example.api.professional;

import com.example.api.address.Address;
import com.example.api.clinicHistory.ClinicHistory;
import com.example.api.email.Email;
import com.example.api.gender.Gender;
import com.example.api.medicalCenter.MedicalCenter;
import com.example.api.phone.Phone;
import com.example.api.realPerson.RealPerson;
import com.example.api.speciality.Speciality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="professional")
@Getter
@Setter
public class Professional extends RealPerson {
    @Column(nullable = false, length = 144)
    private String mp;

    @ManyToOne
    @JoinColumn(name="medical_center_id", nullable = true)
    private MedicalCenter medicalCenter;

    @ManyToOne
    @JoinColumn(name="speciality_id", nullable = false)
    private Speciality speciality;

    @OneToMany(mappedBy = "professional")
    private List<ClinicHistory> clinicHistories;

    public Professional(){

    }
    public Professional(Long id, String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Gender gender, String mp, MedicalCenter medicalCenter, Speciality speciality) {
        super(id, fullName, addresses, phones, emails, firstName, secondName, lastName, secondLastName, birthday, gender);
        this.mp = mp;
        this.medicalCenter = medicalCenter;
        this.speciality = speciality;
    }

    public Professional(String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Gender gender, String mp, MedicalCenter medicalCenter, Speciality speciality) {
        super(fullName, addresses, phones, emails, firstName, secondName, lastName, secondLastName, birthday, gender);
        this.mp = mp;
        this.medicalCenter = medicalCenter;
        this.speciality = speciality;
    }
}
