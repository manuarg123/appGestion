package com.example.api.email;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.emalType.EmailType;
import com.example.api.emalType.EmailTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    HashMap<String, Object> data;

    private final EmailRepository emailRepository;
    private final EmailTypeRepository emailTypeRepository;
    private final PersonRepository personRepository;

    @Autowired EmailService(EmailRepository emailRepository, EmailTypeRepository emailTypeRepository, PersonRepository personRepository){
        this.emailRepository = emailRepository;
        this.emailTypeRepository = emailTypeRepository;
        this.personRepository = personRepository;
    }

    public List<Email> getEmails() {
        return this.emailRepository.findByDeletedAtIsNull();
    }

    public APIResponse newEmail(EmailDTO emailDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<EmailType> optionalEmailType = emailTypeRepository.findByIdAndDeletedAtIsNull(emailDTO.getTypeId());
        if (!optionalEmailType.isPresent()){
            EmailType emailType = null;
        }
        EmailType emailType = optionalEmailType.get();


        Optional<Person> optionalPerson = personRepository.findById(emailDTO.getPersonId());
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();

        Email email = new Email(emailDTO.getValue(), person, emailType);
        emailRepository.save(email);

        data.put("message", MessagesResponse.addSuccess);
        data.put("data", email);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editEmail(Long id, EmailDTO emailDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Email> optionalEmail = emailRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalEmail.isPresent()){
            Email email = optionalEmail.get();

            Optional<EmailType> optionalEmailType = emailTypeRepository.findByIdAndDeletedAtIsNull(emailDTO.getTypeId());
            if (!optionalEmailType.isPresent()){
                EmailType emailType = null;
            }
            EmailType emailType = optionalEmailType.get();

            Optional<Person> optionalPerson = personRepository.findById(emailDTO.getPersonId());
            if(!optionalPerson.isPresent()){
                Person person = null;
            }
            Person person = optionalPerson.get();

            email.setPerson(person);
            email.setType(emailType);
            email.setValue(emailDTO.getValue());

            emailRepository.save(email);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", email);
            apiResponse.setData(data);
        }else{
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteEmail(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.emailRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Email> optionalEmail = emailRepository.findById(id);
        Email existingEmail = optionalEmail.get();
        existingEmail.setDeletedAt(LocalDateTime.now());

        emailRepository.save(existingEmail);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }

    public HashMap createEmail(EmailDTO emailDTO, HashMap data, Long personId){
        Email email = new Email();
        email.setValue(emailDTO.getValue());

        Optional<EmailType> optionalEmailType = emailTypeRepository.findByIdAndDeletedAtIsNull(emailDTO.getTypeId());
        if (!optionalEmailType.isPresent()){
            EmailType emailType = null;
        }
        EmailType emailType = optionalEmailType.get();
        email.setType(emailType);

        Optional<Person> optionalPerson = personRepository.findById(personId);
        if(!optionalPerson.isPresent()){
            Person person = null;
        }
        Person person = optionalPerson.get();
        email.setPerson(person);

        emailRepository.save(email);
        data.put("email-message", MessagesResponse.addSuccess);
        data.put("email", email);

        return data;
    }
}