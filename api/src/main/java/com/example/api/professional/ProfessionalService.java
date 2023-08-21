package com.example.api.professional;

import com.example.api.address.Address;
import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.common.*;
import com.example.api.email.Email;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailService;
import com.example.api.gender.Gender;
import com.example.api.gender.GenderRepository;
import com.example.api.identification.Identification;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationService;
import com.example.api.medicalCenter.MedicalCenter;
import com.example.api.medicalCenter.MedicalCenterDTO;
import com.example.api.medicalCenter.MedicalCenterRepository;
import com.example.api.phone.Phone;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneService;
import com.example.api.speciality.Speciality;
import com.example.api.speciality.SpecialtyRepository;
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
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final IdentificationService identificationService;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final EmailService emailService;
    private final SpecialtyRepository specialtyRepository;
    private final GenderRepository genderRepository;
    private final MedicalCenterRepository medicalCenterRepository;
    private final ProfessionalListDTOMapper professionalListDTOMapper;

    public List<ProfessionalListDTO> getProfessionals() {
        List<ProfessionalListDTO> professionalListDTOS = professionalRepository.findByDeletedAtIsNull()
                .stream()
                .map(professionalListDTOMapper::apply)
                .collect(Collectors.toList());
        return professionalListDTOS;
    }

    public APIResponse newProfessional(ProfessionalDTO professionalDTO) {
        validateProfessional(professionalDTO);

        APIResponse apiResponse = new APIResponse();
        Professional professional = new Professional();
        professional.setMp(professionalDTO.getMp());
        professional.setFirstName(professionalDTO.getFirstName());
        professional.setSecondName(professionalDTO.getSecondName());
        professional.setLastName(professionalDTO.getLastName());
        professional.setSecondLastName(professionalDTO.getSecondLastName());
        professional.setBirthday(professionalDTO.getBirthday());
        professional.setFullName(professionalDTO.getFirstName() + " " + professionalDTO.getLastName());

        Optional<Speciality> optionalSpeciality = findSpecialty(professionalDTO.getSpecialityId());
        professional.setSpeciality(optionalSpeciality.get());
        Optional<Gender> optionalGender = findGender(professionalDTO.getGenderId());
        professional.setGender(optionalGender.get());

        List<MedicalCenter> medicalCenters = new ArrayList<>();
        if (professionalDTO.getMedicalCenterIds() != null) {
            for (Long medicalCenterId : professionalDTO.getMedicalCenterIds()) {
                Optional<MedicalCenter> optionalMedicalCenter = findMedicalCenter(medicalCenterId);
                optionalMedicalCenter.ifPresent(medicalCenters::add);
            }
        }

        professional.setMedicalCenters(medicalCenters);

        professionalRepository.save(professional);

        Long professionalId = professional.getId();

        setDataCollectionsForPerson(professionalId, professionalDTO, true);

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setData(professional);
        return apiResponse;
    }

    @Transactional(rollbackOn = BadRequestException.class)
    public APIResponse editProfessional(Long id, ProfessionalDTO professionalDTO) {
        Optional<Professional> optionalProfessional = findProfessional(id);
        validateProfessional(professionalDTO);

        APIResponse apiResponse = new APIResponse();
        Professional professional = optionalProfessional.get();

        professional.setMp(professionalDTO.getMp());
        professional.setFirstName(professionalDTO.getFirstName());
        professional.setSecondName(professionalDTO.getSecondName());
        professional.setLastName(professionalDTO.getLastName());
        professional.setSecondLastName(professionalDTO.getSecondLastName());
        professional.setBirthday(professionalDTO.getBirthday());
        professional.setFullName(professionalDTO.getFirstName() + " " + professionalDTO.getLastName());

        Optional<Speciality> optionalSpeciality = findSpecialty(professionalDTO.getSpecialityId());
        professional.setSpeciality(optionalSpeciality.get());
        Optional<Gender> optionalGender = findGender(professionalDTO.getGenderId());
        professional.setGender(optionalGender.get());

        List<MedicalCenter> medicalCenters = new ArrayList<>();
        if (professionalDTO.getMedicalCenterIds() != null) {
            for (Long medicalCenterId : professionalDTO.getMedicalCenterIds()) {
                Optional<MedicalCenter> optionalMedicalCenter = findMedicalCenter(medicalCenterId);
                optionalMedicalCenter.ifPresent(medicalCenters::add);
            }
        }

        professional.setMedicalCenters(medicalCenters);

        professionalRepository.save(professional);

        Long professionalId = professional.getId();

        setDataCollectionsForPerson(professionalId, professionalDTO, false);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.editSuccess);
        apiResponse.setData(professional);
        return apiResponse;
    }

    public APIResponse getProfessional(Long id) {
        Optional<Professional> optionalProfessional = findProfessional(id);
        APIResponse apiResponse = new APIResponse();

        apiResponse.setData(optionalProfessional.get());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void setDataCollectionsForPerson(Long professionalId, ProfessionalDTO professionalDTO, Boolean isNew) {
        try {
            if (professionalDTO.getPhones() != null) {
                for (PhoneDTO phoneDTO : professionalDTO.getPhones()) {
                    phoneDTO.setPersonId(professionalId);
                    phoneService.createPhone(phoneDTO);
                }
            }
            if (professionalDTO.getEmails() != null) {
                for (EmailDTO emailDTO : professionalDTO.getEmails()) {
                    emailDTO.setPersonId(professionalId);
                    emailService.createEmail(emailDTO);
                }
            }
            if (professionalDTO.getIdentifications() != null) {
                for (IdentificationDTO identificationDTO : professionalDTO.getIdentifications()) {
                    identificationDTO.setPersonId(professionalId);
                    identificationService.createIdentification(identificationDTO);
                }
            }
            if (professionalDTO.getAddresses() != null) {
                for (AddressDTO addressDTO : professionalDTO.getAddresses()) {
                    addressDTO.setPersonId(professionalId);
                    addressService.createAddress(addressDTO);
                }
            }
        } catch (BadRequestException e) {
            if (isNew) {
                professionalRepository.deleteById(professionalId);
            }
            throw e;
        }
    }

    public void validateProfessional(ProfessionalDTO professionalDTO) {
        if (Stream.of(professionalDTO)
                .anyMatch(dto -> dto.getSpecialityId() == null || dto.getGenderId() == null)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Optional<Speciality> findSpecialty(Long id) {
        Optional<Speciality> optionalSpeciality = specialtyRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalSpeciality.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalSpeciality;
    }

    public Optional<Gender> findGender(Long id) {
        Optional<Gender> optionalGender = genderRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalGender.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalGender;
    }

    public Optional<MedicalCenter> findMedicalCenter(Long id) {
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalMedicalCenter.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalMedicalCenter;
    }

    public Optional<Professional> findProfessional(Long id) {
        Optional<Professional> optionalProfessional = professionalRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalProfessional.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalProfessional;
    }

    public Page<ProfessionalListDTO> getProfessionalsPaginated(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Professional> professionalPage = professionalRepository.findPageByDeletedAtIsNull(pageable);
        Page<ProfessionalListDTO> professionalListDTOS = professionalPage.map(professionalListDTOMapper::apply);

        return professionalListDTOS;
    }

    public APIResponse deleteProfessional(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Professional> optionalProfessional = professionalRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalProfessional.isPresent()){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        Professional existingProfessional = optionalProfessional.get();
        setDeletedAtInDataCollections(existingProfessional);
        existingProfessional.setDeletedAt(LocalDateTime.now());
        professionalRepository.save(existingProfessional);

        apiResponse.setData(existingProfessional);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void setDeletedAtInDataCollections(Professional professional){
        if (professional.getPhones() != null) {
            for (Phone phone : professional.getPhones()) {
                phoneService.deletePhone(phone.getId());
            }
        }

        if (professional.getEmails() != null) {
            for (Email email : professional.getEmails()) {
                emailService.deleteEmail(email.getId());
            }
        }

        if (professional.getAddresses() != null) {
            for (Address address : professional.getAddresses()) {
                addressService.deleteAddress(address.getId());
            }
        }

        if (professional.getIdentifications() != null) {
            for (Identification identification : professional.getIdentifications()) {
                identificationService.deleteIdentification(identification.getId());
            }
        }
    }
}
