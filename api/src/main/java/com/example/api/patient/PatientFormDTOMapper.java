package com.example.api.patient;

import com.example.api.address.AddressDTO;
import com.example.api.address.AddressDTOMapper;
import com.example.api.clinicHistory.ClinicHistoryDTO;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailDTOMapper;
import com.example.api.emergencyContact.EmergencyContact;
import com.example.api.emergencyContact.EmergencyContactDTO;
import com.example.api.emergencyContact.EmergencyContactDTOMapper;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationDTOMapper;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneDTOMapper;
import com.example.api.socialWork.SocialWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientFormDTOMapper implements Function<Patient, PatientDTO> {
    private final EmergencyContactDTOMapper emergencyContactDTOMapper;
    private final AddressDTOMapper addressDTOMapper;
    private final PhoneDTOMapper phoneDTOMapper;
    private final EmailDTOMapper emailDTOMapper;
    private final IdentificationDTOMapper identificationDTOMapper;

    @Override
    public PatientDTO apply(Patient patient) {
        List<Long> socialWorksIds = new ArrayList<>();
        if (patient.getSocialWorks() != null && !patient.getSocialWorks().isEmpty()) {
            for (SocialWork socialWork : patient.getSocialWorks()) {
                Long socialWorkId = socialWork.getId();
                socialWorksIds.add(socialWorkId);
            }
        }

        List<EmergencyContactDTO> emergencyContactDTOS = patient.getEmergencyContacts()
                .stream()
                .map(emergencyContactDTOMapper::apply)
                .collect(Collectors.toList());

        List<ClinicHistoryDTO> clinicHistoryDTOS = new ArrayList<>();

        List<AddressDTO> addressDTOS = patient.getAddresses()
                .stream()
                .map(addressDTOMapper::apply)
                .collect(Collectors.toList());

        List<PhoneDTO> phoneDTOS = patient.getPhones()
                .stream()
                .map(phoneDTOMapper::apply)
                .collect(Collectors.toList());

        List<EmailDTO> emailDTOS = patient.getEmails()
                .stream()
                .map(emailDTOMapper)
                .collect(Collectors.toList());

        List<IdentificationDTO> identificationDTOS = patient.getIdentifications()
                .stream()
                .map(identificationDTOMapper::apply)
                .collect(Collectors.toList());

        return new PatientDTO(
                patient.getId(),
                patient.isSmoker(),
                patient.getOccupation(),
                patient.getMedicalHistory(),
                socialWorksIds,
                emergencyContactDTOS,
                clinicHistoryDTOS,
                patient.getFirstName(),
                patient.getSecondName(),
                patient.getLastName(),
                patient.getSecondLastName(),
                patient.getBirthday(),
                patient.getGender().getId(),
                addressDTOS,
                phoneDTOS,
                emailDTOS,
                identificationDTOS
        );
    }
}
