package com.example.api.identification;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.identificationType.IdentificationType;
import com.example.api.identificationType.IdentificationTypeRepository;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class IdentificationService {
    HashMap<String, Object> data;

    private final IdentificationRepository identificationRepository;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final PersonRepository personRepository;

    @Autowired IdentificationService(IdentificationRepository identificationRepository, IdentificationTypeRepository identificationTypeRepository, PersonRepository personRepository){
        this.identificationRepository = identificationRepository;
        this.identificationTypeRepository = identificationTypeRepository;
        this.personRepository = personRepository;
    }

    public List<Identification> getIdentifications() {
        return this.identificationRepository.findByDeletedAtIsNull();
    }

    public APIResponse newIdentification(IdentificationDTO identificationDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(identificationDTO.getTypeId());
        if (!optionalIdentificationType.isPresent()){
            IdentificationType identificationType = null;
        }
        IdentificationType identificationType = optionalIdentificationType.get();


        Optional<Person> optionalPerson = personRepository.findById(identificationDTO.getPersonId());
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();

        Identification identification = new Identification(identificationDTO.getNumber(), identificationType,person);
        identificationRepository.save(identification);

        data.put("message", MessagesResponse.addSuccess);
        data.put("data", identification);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editIdentification(Long id, IdentificationDTO identificationDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Identification> optionalIdentification = identificationRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalIdentification.isPresent()){
            Identification identification = optionalIdentification.get();

            Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(identificationDTO.getTypeId());
            if (!optionalIdentificationType.isPresent()){
                IdentificationType identificationType = null;
            }
            IdentificationType identificationType = optionalIdentificationType.get();

            Optional<Person> optionalPerson = personRepository.findById(identificationDTO.getPersonId());
            if(!optionalPerson.isPresent()){
                Person person = null;
            }
            Person person = optionalPerson.get();

            identification.setPerson(person);
            identification.setType(identificationType);
            identification.setNumber(identificationDTO.getNumber());

            identificationRepository.save(identification);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", identification);
            apiResponse.setData(data);
        }else{
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteIdentification(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.identificationRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Identification> optionalIdentification = identificationRepository.findById(id);
        Identification existingIdentification = optionalIdentification.get();
        existingIdentification.setDeletedAt(LocalDateTime.now());

        identificationRepository.save(existingIdentification);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }

    public HashMap createIdentification(IdentificationDTO identificationDTO, HashMap data, Long personId){
        Identification identification = new Identification();
        identification.setNumber(identificationDTO.getNumber());

        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(identificationDTO.getTypeId());
        if (!optionalIdentificationType.isPresent()){
            IdentificationType identificationType = null;
        }
        IdentificationType identificationType = optionalIdentificationType.get();
        identification.setType(identificationType);

        Optional<Person> optionalPerson = personRepository.findById(personId);
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();
        identification.setPerson(person);

        identificationRepository.save(identification);
        data.put("identification-message", MessagesResponse.addSuccess);
        data.put("identification", identification);

        return data;
    }
}