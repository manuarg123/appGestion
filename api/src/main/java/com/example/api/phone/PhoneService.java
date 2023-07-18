package com.example.api.phone;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.common.NotValidException;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.phoneType.PhoneType;
import com.example.api.phoneType.PhoneTypeRepository;
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
public class PhoneService {
    HashMap<String, Object> data;

    private final PhoneRepository phoneRepository;
    private final PhoneTypeRepository phoneTypeRepository;
    private final PersonRepository personRepository;

    @Autowired
    PhoneService(PhoneRepository phoneRepository, PhoneTypeRepository phoneTypeRepository, PersonRepository personRepository) {
        this.phoneRepository = phoneRepository;
        this.phoneTypeRepository = phoneTypeRepository;
        this.personRepository = personRepository;
    }

    public List<Phone> getPhones() {
        return this.phoneRepository.findByDeletedAtIsNull();
    }

    public APIResponse newPhone(PhoneDTO phoneDTO) {
        validatePhone(phoneDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<PhoneType> optionalPhoneType = findPhoneType(phoneDTO.getTypeId());
        PhoneType phoneType = optionalPhoneType.get();

        Optional<Person> optionalPerson = findPerson(phoneDTO.getPersonId());
        Person person = optionalPerson.get();

        Phone phone = new Phone(phoneDTO.getValue(), person, phoneType);
        phoneRepository.save(phone);

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(phone);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        return apiResponse;
    }

    public APIResponse editPhone(Long id, PhoneDTO phoneDTO) {
        validatePhone(phoneDTO);

        APIResponse apiResponse = new APIResponse();
        Optional<Phone> optionalPhone = findPhone(id);
        Phone phone = optionalPhone.get();

        Optional<PhoneType> optionalPhoneType = findPhoneType(phoneDTO.getTypeId());
        PhoneType phoneType = optionalPhoneType.get();
        Optional<Person> optionalPerson = findPerson(phoneDTO.getPersonId());
        Person person = optionalPerson.get();

        phone.setPerson(person);
        phone.setType(phoneType);
        phone.setNumber(phoneDTO.getValue());
        phoneRepository.save(phone);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(phone);
        apiResponse.setMessage(MessagesResponse.editSuccess);
        return apiResponse;
    }

    public APIResponse deletePhone(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Phone> optionalPhone = findPhone(id);

        Phone existingPhone = optionalPhone.get();
        existingPhone.setDeletedAt(LocalDateTime.now());
        phoneRepository.save(existingPhone);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingPhone);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public void createPhone(PhoneDTO phoneDTO){
        validatePhone(phoneDTO);
        Optional<PhoneType> optionalPhoneType = findPhoneType(phoneDTO.getTypeId());
        Optional<Person> optionalPerson = findPerson(phoneDTO.getPersonId());
        Person existingPerson = optionalPerson.get();
        PhoneType existingPhoneType = optionalPhoneType.get();

        Phone phone = new Phone();
        phone.setNumber(phoneDTO.getValue());
        phone.setType(existingPhoneType);
        phone.setPerson(existingPerson);
        phoneRepository.save(phone);
    }

    public APIResponse getPhone(Long id) {
        Optional<Phone> optionalPhone = findPhone(id);

        APIResponse apiResponse = new APIResponse();
        Phone existingPhone = optionalPhone.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingPhone);
        return apiResponse;
    }

    public void validatePhone(PhoneDTO phoneDTO) {
        if (Stream.of(phoneDTO)
                .map(PhoneDTO::getValue)
                .anyMatch(value -> value == null || value.isBlank() || value.length() > 45)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(phoneDTO)
                .map(PhoneDTO::getPersonId)
                .anyMatch(personId -> personId == null || String.valueOf(personId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(phoneDTO)
                .map(PhoneDTO::getTypeId)
                .anyMatch(typeId -> typeId == null || String.valueOf(typeId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Optional<Phone> findPhone(Long id) {
        Optional<Phone> optionalPhone = phoneRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalPhone.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return optionalPhone;
    }

    public Optional<PhoneType> findPhoneType(Long id) {
        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalPhoneType.isPresent()) {
            throw new NotFoundException(MessagesResponse.phoneTypeNotFound);
        }
        return optionalPhoneType;
    }

    public Optional<Person> findPerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new NotFoundException(MessagesResponse.personNotFund);
        }
        return optionalPerson;
    }
}
