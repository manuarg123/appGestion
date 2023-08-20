package com.example.api.medicalCenter;

import com.example.api.address.Address;
import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.common.*;
import com.example.api.email.Email;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailService;
import com.example.api.identification.Identification;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationService;
import com.example.api.phone.Phone;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class MedicalCenterService {
    private final MedicalCenterRepository medicalCenterRepository;
    private final IdentificationService identificationService;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final EmailService emailService;
    private final MedicalCenterMinDTOMapper medicalCenterMinDTOMapper;


    public List<MedicalCenter> geMedicalCenters() {
        return medicalCenterRepository.findByDeletedAtIsNull();
    }

    @Transactional(rollbackOn = BadRequestException.class)
    public APIResponse newMedicalCenter(MedicalCenterDTO medicalCenterDTO) {
        validateMedicalCenter(medicalCenterDTO);

        APIResponse apiResponse = new APIResponse();
        MedicalCenter medicalCenter = new MedicalCenter();
        medicalCenter.setName(medicalCenterDTO.getName());
        medicalCenter.setFullName(medicalCenter.getName());

        medicalCenterRepository.save(medicalCenter);

        Long medicalCenterId = medicalCenter.getId();

        setDataCollectionsForPerson(medicalCenterId, medicalCenterDTO, true);

        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findById(medicalCenterId);
        MedicalCenter createdMedicalCenter = optionalMedicalCenter.orElseThrow(() -> new NotFoundException("Medical Center not found"));

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setData(createdMedicalCenter);
        return apiResponse;
    }

    @Transactional(rollbackOn = BadRequestException.class)
    public APIResponse editMedicalCenter(Long id, MedicalCenterDTO medicalCenterDTO) {
        Optional<MedicalCenter> optionalMedicalCenter = findMedicalCenter(id);
        validateMedicalCenter(medicalCenterDTO);

        APIResponse apiResponse = new APIResponse();
        MedicalCenter existingMedicalCenter = optionalMedicalCenter.get();
        existingMedicalCenter.setName(medicalCenterDTO.getName());
        existingMedicalCenter.setFullName(medicalCenterDTO.getName());

        setDataCollectionsForPerson(id, medicalCenterDTO, false);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.editSuccess);
        apiResponse.setData(existingMedicalCenter);

        return apiResponse;
    }

    public APIResponse getMedicalCenter(Long id) {
        Optional<MedicalCenter> optionalMedicalCenter = findMedicalCenter(id);
        APIResponse apiResponse = new APIResponse();

        apiResponse.setData(optionalMedicalCenter.get());
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public Optional<MedicalCenter> findMedicalCenter(Long id) {
        Optional<MedicalCenter> optionalMedicalCenter = this.medicalCenterRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalMedicalCenter.isPresent()) {
            throw new NotFoundException(MessagesResponse.medicalCenterNotFound);
        }
        return optionalMedicalCenter;
    }

    public void validateMedicalCenter(MedicalCenterDTO medicalCenterDTO) {
        if (Stream.of(medicalCenterDTO)
                .map(MedicalCenterDTO::getName)
                .anyMatch(name -> name == null)) {
            throw new NotValidException(MessagesResponse.medicalCenterCannotBeNull);
        }
    }

    public void setDataCollectionsForPerson(Long medicalCenterId, MedicalCenterDTO medicalCenterDTO, Boolean isNew) {
        try {
            if (medicalCenterDTO.getPhones() != null) {
                for (PhoneDTO phoneDTO : medicalCenterDTO.getPhones()) {
                    phoneDTO.setPersonId(medicalCenterId);
                    phoneService.createPhone(phoneDTO);
                }
            }
            if (medicalCenterDTO.getEmails() != null) {
                for (EmailDTO emailDTO : medicalCenterDTO.getEmails()) {
                    emailDTO.setPersonId(medicalCenterId);
                    emailService.createEmail(emailDTO);
                }
            }
            if (medicalCenterDTO.getIdentifications() != null) {
                for (IdentificationDTO identificationDTO : medicalCenterDTO.getIdentifications()) {
                    identificationDTO.setPersonId(medicalCenterId);
                    identificationService.createIdentification(identificationDTO);
                }
            }
            if (medicalCenterDTO.getAddresses() != null) {
                for (AddressDTO addressDTO : medicalCenterDTO.getAddresses()) {
                    addressDTO.setPersonId(medicalCenterId);
                    addressService.createAddress(addressDTO);
                }
            }
        } catch (BadRequestException e) {
            if (isNew){
                medicalCenterRepository.deleteById(medicalCenterId);
            }
            throw e;
        }
    }

    public Page<MedicalCenter> getMedicalCentersPaginated(int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return medicalCenterRepository.findPageByDeletedAtIsNull(pageable);
    }

    public APIResponse deleteMedicalCenter(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalMedicalCenter.isPresent()){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        MedicalCenter existingMedicalCenter = optionalMedicalCenter.get();
        setDeletedAtInDataCollections(existingMedicalCenter);
        existingMedicalCenter.setDeletedAt(LocalDateTime.now());
        medicalCenterRepository.save(existingMedicalCenter);

        apiResponse.setData(existingMedicalCenter);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void setDeletedAtInDataCollections(MedicalCenter medicalCenter){
        if (medicalCenter.getPhones() != null) {
            for (Phone phone : medicalCenter.getPhones()) {
                phoneService.deletePhone(phone.getId());
            }
        }

        if (medicalCenter.getEmails() != null) {
            for (Email email : medicalCenter.getEmails()) {
                emailService.deleteEmail(email.getId());
            }
        }

        if (medicalCenter.getAddresses() != null) {
            for (Address address : medicalCenter.getAddresses()) {
                addressService.deleteAddress(address.getId());
            }
        }

        if (medicalCenter.getIdentifications() != null) {
            for (Identification identification : medicalCenter.getIdentifications()) {
                identificationService.deleteIdentification(identification.getId());
            }
        }
    }

    public List<MedicalCenterMinDTO> getMedicalCentersDtos() {
        List<MedicalCenterMinDTO> medicalCenterMinDTOS = medicalCenterRepository.findByDeletedAtIsNull()
                .stream()
                .map(medicalCenterMinDTOMapper::apply)
                .collect(Collectors.toList());
        return medicalCenterMinDTOS;
    }

    public MedicalCenterMinDTO getMedicalCentersDto(Long id) {
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalMedicalCenter.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        MedicalCenter medicalCenter = optionalMedicalCenter.get();

        return medicalCenterMinDTOMapper.apply(medicalCenter);
    }
}
