package com.example.api.phoneType;

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
public class PhoneTypeService {
    private final PhoneTypeRepository phoneTypeRepository;

    @Autowired
    public PhoneTypeService(PhoneTypeRepository phoneTypeRepository) {
        this.phoneTypeRepository = phoneTypeRepository;
    }

    public List<PhoneType> getPhoneTypes() {
        return this.phoneTypeRepository.findByDeletedAtIsNull();
    }


    public APIResponse newPhoneType(PhoneTypeDTO phoneTypeDTO) {
        if (Stream.of(phoneTypeDTO)
                .map(PhoneTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<PhoneType> res = phoneTypeRepository.findPhoneTypeByName(phoneTypeDTO.getName());

        if (res.isPresent()) {
            PhoneType existingPhoneType = res.get();
            if (existingPhoneType.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        PhoneType phoneType = new PhoneType();
        phoneType.setName(phoneTypeDTO.getName());
        phoneTypeRepository.save(phoneType);

        apiResponse.setData(phoneType);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editPhoneType(Long id, PhoneTypeDTO phoneTypeDTO) {
        if (Stream.of(phoneTypeDTO)
                .map(PhoneTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPhoneType.isPresent()) {
            Optional<PhoneType> res = phoneTypeRepository.findPhoneTypeByName(phoneTypeDTO.getName());

            if (res.isPresent()) {
                PhoneType existingPhoneType = res.get();
                if (existingPhoneType.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            PhoneType existingPhoneType = optionalPhoneType.get();
            existingPhoneType.setName(phoneTypeDTO.getName());
            phoneTypeRepository.save(existingPhoneType);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingPhoneType);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deletePhoneType(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPhoneType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        PhoneType existingPhoneType = optionalPhoneType.get();
        existingPhoneType.setDeletedAt(LocalDateTime.now());
        phoneTypeRepository.save(existingPhoneType);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingPhoneType);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getPhoneType(Long id) {
        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPhoneType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        PhoneType existingPhoneType = optionalPhoneType.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingPhoneType);
        return apiResponse;
    }
}
