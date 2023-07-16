package com.example.api.identificationType;

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
public class IdentificationTypeService {
    private final IdentificationTypeRepository identificationTypeRepository;

    @Autowired
    public IdentificationTypeService(IdentificationTypeRepository identificationTypeRepository) {
        this.identificationTypeRepository = identificationTypeRepository;
    }

    public List<IdentificationType> getIdentificationTypes() {
        return this.identificationTypeRepository.findByDeletedAtIsNull();
    }


    public APIResponse newIdentificationType(IdentificationTypeDTO identificationTypeDTO) {
        if (Stream.of(identificationTypeDTO)
                .map(IdentificationTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<IdentificationType> res = identificationTypeRepository.findIdentificationTypeByName(identificationTypeDTO.getName());

        if (res.isPresent()) {
            IdentificationType existingIdentificationType = res.get();
            if (existingIdentificationType.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        IdentificationType identificationType = new IdentificationType();
        identificationType.setName(identificationTypeDTO.getName());
        identificationTypeRepository.save(identificationType);

        apiResponse.setData(identificationType);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editIdentificationType(Long id, IdentificationTypeDTO identificationTypeDTO) {
        if (Stream.of(identificationTypeDTO)
                .map(IdentificationTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalIdentificationType.isPresent()) {
            Optional<IdentificationType> res = identificationTypeRepository.findIdentificationTypeByName(identificationTypeDTO.getName());

            if (res.isPresent()) {
                IdentificationType existingIdentificationType = res.get();
                if (existingIdentificationType.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            IdentificationType existingIdentificationType = optionalIdentificationType.get();
            existingIdentificationType.setName(identificationTypeDTO.getName());
            identificationTypeRepository.save(existingIdentificationType);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingIdentificationType);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deleteIdentificationType(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalIdentificationType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        IdentificationType existingIdentificationType = optionalIdentificationType.get();
        existingIdentificationType.setDeletedAt(LocalDateTime.now());
        identificationTypeRepository.save(existingIdentificationType);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingIdentificationType);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getIdentificationType(Long id) {
        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalIdentificationType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        IdentificationType existingIdentificationType = optionalIdentificationType.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingIdentificationType);
        return apiResponse;
    }
}
