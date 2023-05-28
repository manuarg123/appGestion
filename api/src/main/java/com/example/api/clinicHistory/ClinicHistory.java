package com.example.api.clinicHistory;

import com.example.api.AuditableEntity;
import com.example.api.patient.Patient;
import com.example.api.professional.Professional;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class ClinicHistory extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private LocalDate date;

    @Column(name="reason_consultation", columnDefinition = "LONGTEXT", nullable=true)
    private String reasonConsultation;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String observations;

    @Column(name="recommended_treatments",columnDefinition = "LONGTEXT", nullable = true)
    private String recommendedTreatments;

    @Column(name="medical_prescriptions", columnDefinition = "LONGTEXT", nullable = true)
    private String medicalPrescriptions;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String diagnoses;

    @Column(nullable = true, name="next_consultation")
    private LocalDate nextConsultation;

    @ManyToOne
    @JoinColumn(name="professional_id", nullable = false)
    private Professional professional;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    public ClinicHistory(){}

    public ClinicHistory(Long id, LocalDate date, String reasonConsultation, String observations, String recommendedTreatments, String medicalPrescriptions, String diagnoses, LocalDate nextConsultation, Professional professional, Patient patient) {
        this.id = id;
        this.date = date;
        this.reasonConsultation = reasonConsultation;
        this.observations = observations;
        this.recommendedTreatments = recommendedTreatments;
        this.medicalPrescriptions = medicalPrescriptions;
        this.diagnoses = diagnoses;
        this.nextConsultation = nextConsultation;
        this.professional = professional;
        this.patient = patient;
    }

    public ClinicHistory(LocalDate date, String reasonConsultation, String observations, String recommendedTreatments, String medicalPrescriptions, String diagnoses, LocalDate nextConsultation, Professional professional, Patient patient) {
        this.date = date;
        this.reasonConsultation = reasonConsultation;
        this.observations = observations;
        this.recommendedTreatments = recommendedTreatments;
        this.medicalPrescriptions = medicalPrescriptions;
        this.diagnoses = diagnoses;
        this.nextConsultation = nextConsultation;
        this.professional = professional;
        this.patient = patient;
    }
}
