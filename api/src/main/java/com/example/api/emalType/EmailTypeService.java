package com.example.api.emalType;

import com.example.api.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Validated
public class EmailTypeService {
    private final EmailTypeRepository emailTypeRepository;

    @Autowired
    public EmailTypeService(EmailTypeRepository emailTypeRepository) {
        this.emailTypeRepository = emailTypeRepository;
    }

    public List<EmailType> getEmailTypes() {
        return this.emailTypeRepository.findByDeletedAtIsNull();
    }


    public APIResponse newEmailType(EmailTypeDTO emailTypeDTO) {
        validateEmailType(emailTypeDTO);

        findDuplicatedEmailType(emailTypeDTO.getName());

        APIResponse apiResponse = new APIResponse();
        EmailType emailType = new EmailType();
        emailType.setName(emailTypeDTO.getName());
        emailTypeRepository.save(emailType);

        apiResponse.setData(emailType);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editEmailType(Long id, EmailTypeDTO emailTypeDTO) {

        validateEmailType(emailTypeDTO);

        APIResponse apiResponse = new APIResponse();

        Optional<EmailType> optionalEmailType = findEmailType(id);
        findDuplicatedEmailType(emailTypeDTO.getName());

        EmailType existingEmailType = optionalEmailType.get();
        existingEmailType.setName(emailTypeDTO.getName());
        emailTypeRepository.save(existingEmailType);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.editSuccess);
        apiResponse.setData(existingEmailType);

        return apiResponse;
    }

    public APIResponse deleteEmailType(Long id) {
        Optional<EmailType> optionalEmailType = findEmailType(id);
        APIResponse apiResponse = new APIResponse();

        EmailType existingEmailType = optionalEmailType.get();
        existingEmailType.setDeletedAt(LocalDateTime.now());
        emailTypeRepository.save(existingEmailType);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingEmailType);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getEmailType(Long id) {
        Optional<EmailType> optionalEmailType = findEmailType(id);

        APIResponse apiResponse = new APIResponse();
        EmailType existingEmailType = optionalEmailType.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingEmailType);
        return apiResponse;
    }

    public void validateEmailType(EmailTypeDTO emailTypeDTO) {
        if (Stream.of(emailTypeDTO)
                .map(EmailTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Optional<EmailType> findEmailType(Long id){
        Optional<EmailType> optionalEmailType = emailTypeRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalEmailType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        } else {
            return optionalEmailType;
        }
    }

    public void findDuplicatedEmailType(String name){
        Optional<EmailType> res = emailTypeRepository.findEmailTypeByName(name);
        if (res.isPresent()) {
            EmailType existingEmailType = res.get();
            if (existingEmailType.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }
    }
}
