package com.example.api.emergencyContact;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.common.NotValidException;
import com.example.api.patient.Patient;
import com.example.api.patient.PatientRepository;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EmergencyContactService {
    private final PatientRepository patientRepository;
    private final EmergencyContactRepository emergencyContactRepository;
    private final EmergencyContactDTOMapper emergencyContactDTOMapper;

    public void createEmergencyContact(EmergencyContactDTO emergencyContactDTO) {
        validateEmergencyContact(emergencyContactDTO);
        Optional<Patient> optionalPatient = findPatient(emergencyContactDTO.getPersonId());

        if (emergencyContactDTO.getId() != null) {
            Optional<EmergencyContact> optionalEmergencyContact = emergencyContactRepository.findByIdAndDeletedAtIsNull(emergencyContactDTO.getId());
            if (optionalEmergencyContact.isPresent()){
                EmergencyContact emergencyContact = optionalEmergencyContact.get();
                emergencyContact.setPatient(optionalPatient.get());
                emergencyContact.setPhoneNumber(emergencyContact.getPhoneNumber());
                emergencyContact.setName(emergencyContactDTO.getName());
                emergencyContactRepository.save(emergencyContact);
                return;
            }
        }

        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setName(emergencyContactDTO.getName());
        emergencyContact.setPhoneNumber(emergencyContactDTO.getPhoneNumber());
        emergencyContact.setPatient(optionalPatient.get());
        emergencyContactRepository.save(emergencyContact);
    }

    public void validateEmergencyContact(EmergencyContactDTO emergencyContactDTO) {
        if (Stream.of(emergencyContactDTO)
                .map(EmergencyContactDTO::getPersonId)
                .anyMatch(personId -> personId == null || String.valueOf(personId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(emergencyContactDTO)
                .map(EmergencyContactDTO::getName)
                .anyMatch(name -> name == null || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(emergencyContactDTO)
                .map(EmergencyContactDTO::getPhoneNumber)
                .anyMatch(phoneNumber -> phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Optional<Patient> findPatient(Long id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (!optionalPatient.isPresent()) {
            throw new NotFoundException(MessagesResponse.personNotFund);
        }
        return optionalPatient;
    }

    public void deleteEmergencyContact(Long id) {
        Optional<EmergencyContact> optionalEmergencyContact = emergencyContactRepository.findById(id);

        EmergencyContact existingEmergencyContact = optionalEmergencyContact.get();
        existingEmergencyContact.setDeletedAt(LocalDateTime.now());
        emergencyContactRepository.save(existingEmergencyContact);
    }

    public APIResponse getEmergencyContact(Long id) {
        Optional<EmergencyContact> optionalEmergencyContact = findEmergencyContact(id);

        APIResponse apiResponse = new APIResponse();
        EmergencyContact emergencyContact = optionalEmergencyContact.get();

        EmergencyContactDTO emergencyContactDTO = new EmergencyContactDTO();
        emergencyContactDTO.setId(emergencyContact.getId());
        emergencyContactDTO.setName(emergencyContact.getName());
        emergencyContactDTO.setPersonId(emergencyContact.getPatient().getId());
        emergencyContactDTO.setPhoneNumber(emergencyContact.getPhoneNumber());

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(emergencyContactDTO);
        return apiResponse;
    }

    public Optional<EmergencyContact> findEmergencyContact(Long id) {
        Optional<EmergencyContact> optionalEmergencyContact = emergencyContactRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalEmergencyContact.isPresent()) {
            throw new NotFoundException(MessagesResponse.emergencyContactNotFound);
        }
        return optionalEmergencyContact;
    }
}
