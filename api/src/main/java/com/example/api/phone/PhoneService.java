package com.example.api.phone;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.phoneType.PhoneType;
import com.example.api.phoneType.PhoneTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {
    HashMap<String, Object> data;

    private final PhoneRepository phoneRepository;
    private final PhoneTypeRepository phoneTypeRepository;
    private final PersonRepository personRepository;

    @Autowired PhoneService(PhoneRepository phoneRepository, PhoneTypeRepository phoneTypeRepository, PersonRepository personRepository){
        this.phoneRepository = phoneRepository;
        this.phoneTypeRepository = phoneTypeRepository;
        this.personRepository = personRepository;
    }

    public List<Phone> getPhones() {
        return this.phoneRepository.findByDeletedAtIsNull();
    }

    public APIResponse newPhone(PhoneDTO phoneDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(phoneDTO.getTypeId());
        if (!optionalPhoneType.isPresent()){
            PhoneType phoneType = null;
        }
        PhoneType phoneType = optionalPhoneType.get();


        Optional<Person> optionalPerson = personRepository.findById(phoneDTO.getPersonId());
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();

        Phone phone = new Phone(phoneDTO.getValue(), person, phoneType);
        phoneRepository.save(phone);

        data.put("message", MessagesResponse.addSuccess);
        data.put("data", phone);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editPhone(Long id, PhoneDTO phoneDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Phone> optionalPhone = phoneRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPhone.isPresent()){
            Phone phone = optionalPhone.get();

            Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(phoneDTO.getTypeId());
            if (!optionalPhoneType.isPresent()){
                PhoneType phoneType = null;
            }
            PhoneType phoneType = optionalPhoneType.get();

            Optional<Person> optionalPerson = personRepository.findById(phoneDTO.getPersonId());
            if(!optionalPerson.isPresent()){
                Person person = null;
            }
            Person person = optionalPerson.get();

            phone.setPerson(person);
            phone.setType(phoneType);
            phone.setNumber(phoneDTO.getValue());

            phoneRepository.save(phone);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", phone);
            apiResponse.setData(data);
        }else{
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deletePhone(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.phoneRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        Phone existingPhone = optionalPhone.get();
        existingPhone.setDeletedAt(LocalDateTime.now());

        phoneRepository.save(existingPhone);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }

    public HashMap createPhone(PhoneDTO phoneDTO, HashMap data, Long personId){
        Phone phone = new Phone();
        phone.setNumber(phoneDTO.getValue());

        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(phoneDTO.getTypeId());
        if (!optionalPhoneType.isPresent()){
            PhoneType phoneType = null;
        }
        PhoneType phoneType = optionalPhoneType.get();
        phone.setType(phoneType);

        Optional<Person> optionalPerson = personRepository.findById(personId);
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();
        phone.setPerson(person);

        phoneRepository.save(phone);
        data.put("phone-message", MessagesResponse.addSuccess);
        data.put("phone", phone);

        return data;
    }
}
