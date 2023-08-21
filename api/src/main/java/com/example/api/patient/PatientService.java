package com.example.api.patient;

import com.example.api.address.Address;
import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.clinicHistory.ClinicHistoryDTO;
import com.example.api.common.*;
import com.example.api.email.Email;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailService;
import com.example.api.emergencyContact.EmergencyContact;
import com.example.api.emergencyContact.EmergencyContactDTO;
import com.example.api.emergencyContact.EmergencyContactRepository;
import com.example.api.emergencyContact.EmergencyContactService;
import com.example.api.gender.Gender;
import com.example.api.gender.GenderRepository;
import com.example.api.identification.Identification;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationService;
import com.example.api.phone.Phone;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneService;
import com.example.api.socialWork.SocialWork;
import com.example.api.socialWork.SocialWorkRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final IdentificationService identificationService;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final EmailService emailService;
    private final GenderRepository genderRepository;
    private final SocialWorkRepository socialWorkRepository;
    private final EmergencyContactService emergencyContactService;
    private final PatientFormDTOMapper patientFormDTOMapper;
    private final PatientListDTOMapper patientListDTOMapper;

    public List<PatientListDTO> getPatients() {
        List<PatientListDTO> patientListDTOS = patientRepository.findByDeletedAtIsNull()
                .stream()
                .map(patientListDTOMapper::apply)
                .collect(Collectors.toList());

        return patientListDTOS;
    }

    public Page<PatientListDTO> getPatientsPaginated(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Patient> patientPage = patientRepository.findPageByDeletedAtIsNull(pageable);
        Page<PatientListDTO> patientListDTOS = patientPage.map(patientListDTOMapper::apply);

        return patientListDTOS;
    }

    public APIResponse newPatient(PatientDTO patientDTO) {
        validatePatient(patientDTO);
        APIResponse apiResponse = new APIResponse();
        Patient patient = new Patient();
        patient.setSmoker(patientDTO.isSmoker());
        patient.setOccupation(patientDTO.getOccupation());
        patient.setMedicalHistory(patientDTO.getMedicalHistory());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthday(patientDTO.getBirthday());
        patient.setFullName(patientDTO.getFirstName() + " " + patientDTO.getLastName());
        Optional<Gender> optionalGender = findGender(patientDTO.getGenderId());
        patient.setGender(optionalGender.get());

        List<SocialWork> socialWorks = new ArrayList<>();
        if (patientDTO.getSocialWorksIds() != null) {
            for (Long socialWorkId : patientDTO.getSocialWorksIds()) {
                Optional<SocialWork> optionalSocialWork = findSocialWork(socialWorkId);
                optionalSocialWork.ifPresent(socialWorks::add);
            }
        }
        patient.setSocialWorks(socialWorks);
        patientRepository.save(patient);

        Long patientId = patient.getId();

        setDataCollectionsForPerson(patientId, patientDTO, true);

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setData(patient);
        return apiResponse;
    }


    @Transactional(rollbackOn = BadRequestException.class)
    public APIResponse editPatient(Long id, PatientDTO patientDTO) {
        Optional<Patient> optionalPatient = findPatient(id);
        validatePatient(patientDTO);

        APIResponse apiResponse = new APIResponse();
        Patient patient = optionalPatient.get();
        patient.setSmoker(patientDTO.isSmoker());
        patient.setOccupation(patientDTO.getOccupation());
        patient.setMedicalHistory(patientDTO.getMedicalHistory());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthday(patientDTO.getBirthday());
        patient.setFullName(patientDTO.getFirstName() + " " + patientDTO.getLastName());

        Optional<Gender> optionalGender = findGender(patientDTO.getGenderId());
        patient.setGender(optionalGender.get());

        List<SocialWork> socialWorks = new ArrayList<>();
        if (patientDTO.getSocialWorksIds() != null) {
            for (Long socialWorkId : patientDTO.getSocialWorksIds()) {
                Optional<SocialWork> optionalSocialWork = findSocialWork(socialWorkId);
                optionalSocialWork.ifPresent(socialWorks::add);
            }
        }
        patient.setSocialWorks(socialWorks);
        patientRepository.save(patient);

        Long patientId = patient.getId();

        setDataCollectionsForPerson(patientId, patientDTO, false);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.editSuccess);
        apiResponse.setData(patient);
        return apiResponse;
    }

    private Optional<SocialWork> findSocialWork(Long socialWorkId) {
        Optional<SocialWork> optionalSocialWork = socialWorkRepository.findByIdAndDeletedAtIsNull(socialWorkId);

        if (!optionalSocialWork.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalSocialWork;
    }

    public APIResponse getPatient(Long id) {
        Optional<Patient> optionalPatient = patientRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPatient.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        Patient patient = optionalPatient.get();

        PatientDTO patientDTO = patientFormDTOMapper.apply(patient);

        apiResponse.setData(patientDTO);
        apiResponse.setStatus(HttpStatus.OK.value());

        return apiResponse;
    }

    public void validatePatient(PatientDTO patientDTO) {
        if (Stream.of(patientDTO)
                .anyMatch(dto -> dto.getGenderId() == null)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public void setDataCollectionsForPerson(Long patientId, PatientDTO patientDTO, Boolean isNew) {
        try {
            if (patientDTO.getPhones() != null) {
                for (PhoneDTO phoneDTO : patientDTO.getPhones()) {
                    phoneDTO.setPersonId(patientId);
                    phoneService.createPhone(phoneDTO);
                }
            }
            if (patientDTO.getEmails() != null) {
                for (EmailDTO emailDTO : patientDTO.getEmails()) {
                    emailDTO.setPersonId(patientId);
                    emailService.createEmail(emailDTO);
                }
            }
            if (patientDTO.getIdentifications() != null) {
                for (IdentificationDTO identificationDTO : patientDTO.getIdentifications()) {
                    identificationDTO.setPersonId(patientId);
                    identificationService.createIdentification(identificationDTO);
                }
            }
            if (patientDTO.getAddresses() != null) {
                for (AddressDTO addressDTO : patientDTO.getAddresses()) {
                    addressDTO.setPersonId(patientId);
                    addressService.createAddress(addressDTO);
                }
            }

            if (patientDTO.getEmergencyContacts() != null) {
                for (EmergencyContactDTO emergencyContactDTO : patientDTO.getEmergencyContacts()) {
                    emergencyContactDTO.setPersonId(patientId);
                    emergencyContactService.createEmergencyContact(emergencyContactDTO);
                }
            }

            //Pendiente
            if (patientDTO.getClinicHistories() != null) {
                for (ClinicHistoryDTO clinicHistoryDTO : patientDTO.getClinicHistories()) {
                    System.out.println(clinicHistoryDTO);
                }
            }
        } catch (BadRequestException e) {
            if (isNew) {
                patientRepository.deleteById(patientId);
            }
            throw e;
        }
    }

    public Optional<Gender> findGender(Long id) {
        Optional<Gender> optionalGender = genderRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalGender.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalGender;
    }

    public Optional<Patient> findPatient(Long id) {
        Optional<Patient> optionalPatient = patientRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalPatient.isPresent()) {
            throw new NotFoundException(MessagesResponse.personNotFund);
        }
        return optionalPatient;
    }

    public APIResponse deletePatient(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Patient> optionalPatient = patientRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPatient.isPresent()){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        Patient existingPatient = optionalPatient.get();
        setDeletedAtInDataCollections(existingPatient);
        existingPatient.setDeletedAt(LocalDateTime.now());
        patientRepository.save(existingPatient);

        apiResponse.setData(existingPatient);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void setDeletedAtInDataCollections(Patient patient){
        if (patient.getPhones() != null) {
            for (Phone phone : patient.getPhones()) {
                phoneService.deletePhone(phone.getId());
            }
        }

        if (patient.getEmails() != null) {
            for (Email email : patient.getEmails()) {
                emailService.deleteEmail(email.getId());
            }
        }

        if (patient.getAddresses() != null) {
            for (Address address : patient.getAddresses()) {
                addressService.deleteAddress(address.getId());
            }
        }

        if (patient.getIdentifications() != null) {
            for (Identification identification : patient.getIdentifications()) {
                identificationService.deleteIdentification(identification.getId());
            }
        }

        if (patient.getEmergencyContacts() != null) {
            for (EmergencyContact emergencyContact: patient.getEmergencyContacts()) {
                emergencyContactService.deleteEmergencyContact(emergencyContact.getId());
            }
        }
    }
}
