package com.example.api.speciality;

import com.example.api.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<Speciality> getSpecialities() {
        return this.specialtyRepository.findByDeletedAtIsNull();
    }

    public APIResponse newSpeciality(SpecialtyDTO specialtyDTO) {
        if (Stream.of(specialtyDTO)
                .map(SpecialtyDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<Speciality> res = specialtyRepository.findSpecialityByName(specialtyDTO.getName());

        if (res.isPresent()) {
            Speciality existingSpecialty = res.get();
            if (existingSpecialty.getDeletedAt() == null){
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        Speciality speciality = new Speciality();
        speciality.setName(specialtyDTO.getName());
        specialtyRepository.save(speciality);

        apiResponse.setData(speciality);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editSpeciality(Long id, SpecialtyDTO specialtyDTO) {
        if (Stream.of(specialtyDTO)
                .map(SpecialtyDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<Speciality> optionalSpeciality = specialtyRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalSpeciality.isPresent()) {
            Optional<Speciality> res = specialtyRepository.findSpecialityByName(specialtyDTO.getName());

            if (res.isPresent()) {
                Speciality existingSpecialty = res.get();
                if (existingSpecialty.getDeletedAt() == null){
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            Speciality existingSpeciality = optionalSpeciality.get();
            existingSpeciality.setName(specialtyDTO.getName());
            specialtyRepository.save(existingSpeciality);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingSpeciality);
        } else {
           throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }


    public APIResponse deleteSpeciality(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Speciality> optionalSpeciality = specialtyRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalSpeciality.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        Speciality existingSpeciality = optionalSpeciality.get();
        existingSpeciality.setDeletedAt(LocalDateTime.now());
        specialtyRepository.save(existingSpeciality);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingSpeciality);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getSpeciality(Long id) {
        Optional<Speciality> optionalSpeciality = specialtyRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalSpeciality.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        Speciality existingSpeciality = optionalSpeciality.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingSpeciality);
        return apiResponse;
    }
}
