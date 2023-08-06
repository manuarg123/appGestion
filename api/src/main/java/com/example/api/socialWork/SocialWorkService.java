package com.example.api.socialWork;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SocialWorkService {
    private final SocialWorkRepository socialWorkRepository;
    private final IdentificationService identificationService;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final EmailService emailService;

    @Autowired
    public SocialWorkService(SocialWorkRepository socialWorkRepository, AddressService addressService, IdentificationService identificationService, PhoneService phoneService, EmailService emailService) {
        this.socialWorkRepository = socialWorkRepository;
        this.addressService = addressService;
        this.identificationService = identificationService;
        this.phoneService = phoneService;
        this.emailService = emailService;
    }

    public List<SocialWork> geSocialWorks() {
        return socialWorkRepository.findByDeletedAtIsNull();
    }

    @Transactional(rollbackOn = BadRequestException.class)
    public APIResponse newSocialWork(SocialWorkDTO socialWorkDTO) {
        validateSocialWork(socialWorkDTO);

        APIResponse apiResponse = new APIResponse();
        SocialWork socialWork = new SocialWork();
        socialWork.setName(socialWorkDTO.getName());
        socialWork.setFullName(socialWork.getName());

        socialWorkRepository.save(socialWork);

        Long socialWorkId = socialWork.getId();

        setDataCollectionsForPerson(socialWorkId, socialWorkDTO, true);

        Optional<SocialWork> optionalSocialWork = socialWorkRepository.findById(socialWorkId);
        SocialWork createdSocialWork = optionalSocialWork.orElseThrow(() -> new NotFoundException("Medical Center not found"));

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setData(createdSocialWork);
        return apiResponse;
    }

    @Transactional(rollbackOn = BadRequestException.class)
    public APIResponse editSocialWork(Long id, SocialWorkDTO socialWorkDTO) {
        Optional<SocialWork> optionalSocialWork = findSocialWork(id);
        validateSocialWork(socialWorkDTO);

        APIResponse apiResponse = new APIResponse();
        SocialWork existingSocialWork = optionalSocialWork.get();
        existingSocialWork.setName(socialWorkDTO.getName());
        existingSocialWork.setFullName(socialWorkDTO.getName());

        setDataCollectionsForPerson(id, socialWorkDTO, false);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.editSuccess);
        apiResponse.setData(existingSocialWork);

        return apiResponse;
    }

    public APIResponse getSocialWork(Long id) {
        Optional<SocialWork> optionalSocialWork = findSocialWork(id);
        APIResponse apiResponse = new APIResponse();

        SocialWork existingSocialWork = optionalSocialWork.get();
        apiResponse.setData(existingSocialWork);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public Optional<SocialWork> findSocialWork(Long id) {
        Optional<SocialWork> optionalSocialWork = this.socialWorkRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalSocialWork.isPresent()) {
            throw new NotFoundException(MessagesResponse.socialWorkNotFound);
        }
        return optionalSocialWork;
    }

    public void validateSocialWork(SocialWorkDTO socialWorkDTO) {
        if (Stream.of(socialWorkDTO)
                .map(SocialWorkDTO::getName)
                .anyMatch(name -> name == null)) {
            throw new NotValidException(MessagesResponse.socialWorkCannotBeNull);
        }
    }

    public void setDataCollectionsForPerson(Long socialWorkId, SocialWorkDTO socialWorkDTO, Boolean isNew) {
        try {
            if (socialWorkDTO.getPhones() != null) {
                for (PhoneDTO phoneDTO : socialWorkDTO.getPhones()) {
                    phoneDTO.setPersonId(socialWorkId);
                    phoneService.createPhone(phoneDTO);
                }
            }
            if (socialWorkDTO.getEmails() != null) {
                for (EmailDTO emailDTO : socialWorkDTO.getEmails()) {
                    emailDTO.setPersonId(socialWorkId);
                    emailService.createEmail(emailDTO);
                }
            }
            if (socialWorkDTO.getIdentifications() != null) {
                for (IdentificationDTO identificationDTO : socialWorkDTO.getIdentifications()) {
                    identificationDTO.setPersonId(socialWorkId);
                    identificationService.createIdentification(identificationDTO);
                }
            }
            if (socialWorkDTO.getAddresses() != null) {
                for (AddressDTO addressDTO : socialWorkDTO.getAddresses()) {
                    addressDTO.setPersonId(socialWorkId);
                    addressService.createAddress(addressDTO);
                }
            }
        } catch (BadRequestException e) {
            if (isNew){
                socialWorkRepository.deleteById(socialWorkId);
            }
            throw e;
        }
    }

    public Page<SocialWork> getSocialWorksPaginated(int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return socialWorkRepository.findPageByDeletedAtIsNull(pageable);
    }

    public APIResponse deleteSocialWork(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<SocialWork> optionalSocialWork = socialWorkRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalSocialWork.isPresent()){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        SocialWork existingSocialWork = optionalSocialWork.get();
        setDeletedAtInDataCollections(existingSocialWork);
        existingSocialWork.setDeletedAt(LocalDateTime.now());
        socialWorkRepository.save(existingSocialWork);

        apiResponse.setData(existingSocialWork);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void setDeletedAtInDataCollections(SocialWork socialWork){
        if (socialWork.getPhones() != null) {
            for (Phone phone : socialWork.getPhones()) {
                phoneService.deletePhone(phone.getId());
            }
        }

        if (socialWork.getEmails() != null) {
            for (Email email : socialWork.getEmails()) {
                emailService.deleteEmail(email.getId());
            }
        }

        if (socialWork.getAddresses() != null) {
            for (Address address : socialWork.getAddresses()) {
                addressService.deleteAddress(address.getId());
            }
        }

        if (socialWork.getIdentifications() != null) {
            for (Identification identification : socialWork.getIdentifications()) {
                identificationService.deleteIdentification(identification.getId());
            }
        }
    }
}
