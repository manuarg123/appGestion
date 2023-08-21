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

    private List<EmergencyContactDTO> emergencyContacts;

    private List<ClinicHistoryDTO> clinicHistories;

    public PatientDTO(Long id, Boolean isSmoker,String occupation, String medicalHistory, List<Long> socialWorksIds, List<EmergencyContactDTO> emergencyContactDTOS, List<ClinicHistoryDTO> clinicHistoryDTOS, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Long genderId, List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS) {
        super(genderId, firstName, secondName, lastName, secondLastName, birthday, addressDTOS, phoneDTOS, emailDTOS, identificationDTOS);
        this.occupation = occupation;
        this.medicalHistory = medicalHistory;
        this.socialWorksIds = socialWorksIds;
        this.emergencyContacts = emergencyContactDTOS;
        this.clinicHistories = clinicHistoryDTOS;
        this.isSmoker = isSmoker;
        this.id = id;
    }

    public PatientDTO(){
        super();
    }
}
