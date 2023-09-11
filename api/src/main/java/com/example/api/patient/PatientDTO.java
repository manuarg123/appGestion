package com.example.api.patient;

import com.example.api.address.AddressDTO;
import com.example.api.clinicHistory.ClinicHistoryDTO;
import com.example.api.email.EmailDTO;
import com.example.api.emergencyContact.EmergencyContactDTO;
import com.example.api.identification.IdentificationDTO;
import com.example.api.phone.PhoneDTO;
import com.example.api.realPerson.RealPersonDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatientDTO extends RealPersonDTO {

    private Long id;

    private boolean isSmoker;

    @Size(max = 255)
    private String occupation;

    private String medicalHistory;

    private List<Long> socialWorksIds;

    private List<Long> plansIds;

    private List<EmergencyContactDTO> emergencyContacts;

    private List<ClinicHistoryDTO> clinicHistories;

    public PatientDTO(List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS, Long id, boolean isSmoker, String occupation, String medicalHistory, List<Long> socialWorksIds, List<Long> plansIds, List<EmergencyContactDTO> emergencyContacts, List<ClinicHistoryDTO> clinicHistories) {
        super(addressDTOS, phoneDTOS, emailDTOS, identificationDTOS);
        this.id = id;
        this.isSmoker = isSmoker;
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWorksIds = socialWorksIds;
        this.plansIds = plansIds;
        this.emergencyContacts = emergencyContacts;
        this.clinicHistories = clinicHistories;
    }

    public PatientDTO(Long genderId, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS, Long id, boolean isSmoker, String occupation, String medicalHistory, List<Long> socialWorksIds, List<Long> plansIds, List<EmergencyContactDTO> emergencyContacts, List<ClinicHistoryDTO> clinicHistories) {
        super(genderId, firstName, secondName, lastName, secondLastName, birthday, addressDTOS, phoneDTOS, emailDTOS, identificationDTOS);
        this.id = id;
        this.isSmoker = isSmoker;
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWorksIds = socialWorksIds;
        this.plansIds = plansIds;
        this.emergencyContacts = emergencyContacts;
        this.clinicHistories = clinicHistories;
    }

    public PatientDTO(Long id, boolean isSmoker, String occupation, String medicalHistory, List<Long> socialWorksIds, List<Long> plansIds, List<EmergencyContactDTO> emergencyContacts, List<ClinicHistoryDTO> clinicHistories) {
        this.id = id;
        this.isSmoker = isSmoker;
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWorksIds = socialWorksIds;
        this.plansIds = plansIds;
        this.emergencyContacts = emergencyContacts;
        this.clinicHistories = clinicHistories;
    }

    public PatientDTO(){
        super();
    }
}
