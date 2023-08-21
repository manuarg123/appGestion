package com.example.api.patient;

import com.example.api.address.Address;
import com.example.api.clinicHistory.ClinicHistory;
import com.example.api.email.Email;
import com.example.api.emergencyContact.EmergencyContact;
import com.example.api.gender.Gender;
import com.example.api.identification.Identification;
import com.example.api.phone.Phone;
import com.example.api.realPerson.RealPerson;
import com.example.api.socialWork.SocialWork;
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
@Table(name="patient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends RealPerson {
    @Column(name="is_smoker")
    private boolean isSmoker;

    @Column(nullable = true, length = 255)
    private String occupation;

    @Column(name="medical_history", columnDefinition = "LONGTEXT")
    private String medicalHistory;

    @ManyToMany
    @JoinTable(name = "patient_social_work",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "social_work_id"))
    private List<SocialWork> socialWorks;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    @Where(clause = "deleted_at IS NULL")
    private List<EmergencyContact> emergencyContacts;

    @OneToMany(mappedBy = "patient")
    private List<ClinicHistory> clinicHistories;
}
