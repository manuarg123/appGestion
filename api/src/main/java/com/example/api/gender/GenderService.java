package com.example.api.gender;

import com.example.api.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Validated
public class GenderService {
    private final GenderRepository genderRepository;

    @Autowired
    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public List<Gender> getGenders() {
        return this.genderRepository.findByDeletedAtIsNull();
    }


    public APIResponse newGender(GenderDTO genderDTO) {
        if (Stream.of(genderDTO)
                .map(GenderDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<Gender> res = genderRepository.findGenderByNameAndDeletedAtIsNull(genderDTO.getName());

        if (res.isPresent()) {
            Gender existingGender = res.get();
            if (existingGender.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        Gender gender = new Gender();
        gender.setName(genderDTO.getName());
        genderRepository.save(gender);

        apiResponse.setData(gender);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editGender(Long id, GenderDTO genderDTO) {
        if (Stream.of(genderDTO)
                .map(GenderDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<Gender> optionalGender = genderRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalGender.isPresent()) {
            Optional<Gender> res = genderRepository.findGenderByNameAndDeletedAtIsNull(genderDTO.getName());

            if (res.isPresent()) {
                Gender existingGender = res.get();
                if (existingGender.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            Gender existingGender = optionalGender.get();
            existingGender.setName(genderDTO.getName());
            genderRepository.save(existingGender);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingGender);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deleteGender(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Gender> optionalGender = genderRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalGender.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        Gender existingGender = optionalGender.get();
        existingGender.setDeletedAt(LocalDateTime.now());
        genderRepository.save(existingGender);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingGender);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getGender(Long id) {
        Optional<Gender> optionalGender = genderRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalGender.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        Gender existingGender = optionalGender.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingGender);
        return apiResponse;
    }

    public Page<Gender> getGendersPaginated(int currentPage, int pageSize){
        int startIndex = (currentPage - 1) * pageSize;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return genderRepository.findPageByDeletedAtIsNull(pageable);
    }
}
