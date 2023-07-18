package com.example.api.identification;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.common.NotValidException;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.identificationType.IdentificationType;
import com.example.api.identificationType.IdentificationTypeRepository;
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
public class IdentificationService {
    HashMap<String, Object> data;

    private final IdentificationRepository identificationRepository;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final PersonRepository personRepository;

    @Autowired
    IdentificationService(IdentificationRepository identificationRepository, IdentificationTypeRepository identificationTypeRepository, PersonRepository personRepository) {
        this.identificationRepository = identificationRepository;
        this.identificationTypeRepository = identificationTypeRepository;
        this.personRepository = personRepository;
    }

    public List<Identification> getIdentifications() {
        return this.identificationRepository.findByDeletedAtIsNull();
    }

    public APIResponse newIdentification(IdentificationDTO identificationDTO) {
        validateIdentification(identificationDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<IdentificationType> optionalIdentificationType = findIdentificationType(identificationDTO.getTypeId());
        IdentificationType identificationType = optionalIdentificationType.get();

        Optional<Person> optionalPerson = findPerson(identificationDTO.getPersonId());
        Person person = optionalPerson.get();

        Identification identification = new Identification(identificationDTO.getValue(), identificationType, person);
        identificationRepository.save(identification);

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(identification);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        return apiResponse;
    }

    public APIResponse editIdentification(Long id, IdentificationDTO identificationDTO) {
        validateIdentification(identificationDTO);

        APIResponse apiResponse = new APIResponse();
        Optional<Identification> optionalIdentification = findIdentification(id);
        Identification identification = optionalIdentification.get();

        Optional<IdentificationType> optionalIdentificationType = findIdentificationType(identificationDTO.getTypeId());
        IdentificationType identificationType = optionalIdentificationType.get();
        Optional<Person> optionalPerson = findPerson(identificationDTO.getPersonId());
        Person person = optionalPerson.get();

        identification.setPerson(person);
        identification.setType(identificationType);
        identification.setNumber(identificationDTO.getValue());
        identificationRepository.save(identification);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(identification);
        apiResponse.setMessage(MessagesResponse.editSuccess);
        return apiResponse;
    }

    public APIResponse deleteIdentification(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Identification> optionalIdentification = findIdentification(id);

        Identification existingIdentification = optionalIdentification.get();
        existingIdentification.setDeletedAt(LocalDateTime.now());
        identificationRepository.save(existingIdentification);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingIdentification);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void createIdentification(IdentificationDTO identificationDTO){
        validateIdentification(identificationDTO);
        Optional<IdentificationType> optionalIdentificationType = findIdentificationType(identificationDTO.getTypeId());
        Optional<Person> optionalPerson = findPerson(identificationDTO.getPersonId());
        Person existingPerson = optionalPerson.get();
        IdentificationType existingIdentificationType = optionalIdentificationType.get();

        Identification identification = new Identification();
        identification.setNumber(identificationDTO.getValue());
        identification.setType(existingIdentificationType);
        identification.setPerson(existingPerson);
        identificationRepository.save(identification);
    }

    public APIResponse getIdentification(Long id) {
        Optional<Identification> optionalIdentification = findIdentification(id);

        APIResponse apiResponse = new APIResponse();
        Identification existingIdentification = optionalIdentification.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingIdentification);
        return apiResponse;
    }

    public void validateIdentification(IdentificationDTO identificationDTO) {
        if (Stream.of(identificationDTO)
                .map(IdentificationDTO::getValue)
                .anyMatch(value -> value == null || value.isBlank() || value.length() > 45)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(identificationDTO)
                .map(IdentificationDTO::getPersonId)
                .anyMatch(personId -> personId == null || String.valueOf(personId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(identificationDTO)
                .map(IdentificationDTO::getTypeId)
                .anyMatch(typeId -> typeId == null || String.valueOf(typeId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Optional<Identification> findIdentification(Long id) {
        Optional<Identification> optionalIdentification = identificationRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalIdentification.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return optionalIdentification;
    }

    public Optional<IdentificationType> findIdentificationType(Long id) {
        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalIdentificationType.isPresent()) {
            throw new NotFoundException(MessagesResponse.identificationTypeNotFound);
        }
        return optionalIdentificationType;
    }

    public Optional<Person> findPerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new NotFoundException(MessagesResponse.personNotFund);
        }
        return optionalPerson;
    }
}
