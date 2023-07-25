package com.example.api.patient;

import com.example.api.address.Address;
import com.example.api.clinicHistory.ClinicHistory;
import com.example.api.email.Email;
import com.example.api.emergencyContact.EmergencyContact;
import com.example.api.gender.Gender;
import com.example.api.identification.Identification;
import com.example.api.person.Person;
import com.example.api.phone.Phone;
import com.example.api.realPerson.RealPerson;
import com.example.api.socialWork.SocialWork;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="patient")
@Getter
@Setter
public class Patient extends RealPerson {
    @Column(name="is_smoker")
    private boolean isSmoker;

    @Column(nullable = true, length = 255)
    private String occupation;

    @Column(name="medical_history", columnDefinition = "LONGTEXT")
    private String medicalHistory;

    @ManyToOne
    @JoinColumn(name="social_work_id", nullable = true)
    private SocialWork socialWork;

    @ManyToOne
    @JoinColumn(name="emergency_contact_id", nullable = true)
    protected EmergencyContact emergencyContact;

    @OneToMany(mappedBy = "patient")
    private List<ClinicHistory> clinicHistories;

    public Patient(){}

    public Patient(Long id, String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, List<Identification> identifications, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Gender gender, boolean isSmoker, String occupation, String medicalHistory, SocialWork socialWork, EmergencyContact emergencyContact, List<ClinicHistory> clinicHistories) {
        super(id, fullName, addresses, phones, emails, identifications, firstName, secondName, lastName, secondLastName, birthday, gender);
        this.isSmoker = isSmoker;
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWork = socialWork;
        this.emergencyContact = emergencyContact;
        this.clinicHistories = clinicHistories;
    }

    public Patient(String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, List<Identification> identifications, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Gender gender, boolean isSmoker, String occupation, String medicalHistory, SocialWork socialWork, EmergencyContact emergencyContact, List<ClinicHistory> clinicHistories) {
        super(fullName, addresses, phones, emails, identifications, firstName, secondName, lastName, secondLastName, birthday, gender);
        this.isSmoker = isSmoker;
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWork = socialWork;
        this.emergencyContact = emergencyContact;
        this.clinicHistories = clinicHistories;
    }
}
