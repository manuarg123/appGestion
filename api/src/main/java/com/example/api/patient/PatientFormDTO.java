package com.example.api.patient;

import com.example.api.address.AddressFormDTO;
import com.example.api.clinicHistory.ClinicHistoryDTO;
import com.example.api.email.EmailFormDTO;
import com.example.api.emergencyContact.EmergencyContactDTO;
import com.example.api.identification.IdentificationFormDTO;
import com.example.api.phone.PhoneFormDTO;
import com.example.api.plan.PlanSocialWorkDTO;
import com.example.api.realPerson.RealPersonFormDTO;
import com.example.api.socialWork.SocialWorkPlanDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientFormDTO extends RealPersonFormDTO {
    private Long id;
    private Boolean isSmoker;
    private String occupation;
    private String medicalHistory;
    private List<Long> socialWorksIds;
    private List<Long> planIds;
    private List<EmergencyContactDTO> emergencyContacts;
    private List<ClinicHistoryDTO> clinicHistories;
    private List<PlanSocialWorkDTO> planSocialWorkDTOS;
    @Builder
    public PatientFormDTO(String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Long genderId, String genderName, Long id, Boolean isSmoker, String occupation, String medicalHistory, List<Long> socialWorksIds,  List<Long> plansIds, List<EmergencyContactDTO> emergencyContacts, List<ClinicHistoryDTO> clinicHistories, List<PlanSocialWorkDTO> planSocialWorkDTOS, List<AddressFormDTO> addresses, List<PhoneFormDTO> phones, List<EmailFormDTO> emails, List<IdentificationFormDTO> idenfications) {
        super(addresses,phones, emails, idenfications, firstName,secondName,lastName, secondLastName, birthday, genderId, genderName);
        this.id = id;
        this.isSmoker = isSmoker;
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWorksIds = socialWorksIds;
        this.planIds = plansIds;
        this.emergencyContacts = emergencyContacts;
        this.clinicHistories = clinicHistories;
        this.planSocialWorkDTOS = planSocialWorkDTOS;
    }
}
