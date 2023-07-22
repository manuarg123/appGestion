package com.example.api.email;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.common.NotValidException;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.emalType.EmailType;
import com.example.api.emalType.EmailTypeRepository;
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
public class EmailService {
    HashMap<String, Object> data;

    private final EmailRepository emailRepository;
    private final EmailTypeRepository emailTypeRepository;
    private final PersonRepository personRepository;

    @Autowired
    EmailService(EmailRepository emailRepository, EmailTypeRepository emailTypeRepository, PersonRepository personRepository) {
        this.emailRepository = emailRepository;
        this.emailTypeRepository = emailTypeRepository;
        this.personRepository = personRepository;
    }

    public List<Email> getEmails() {
        return this.emailRepository.findByDeletedAtIsNull();
    }

    public APIResponse newEmail(EmailDTO emailDTO) {
        validateEmail(emailDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<EmailType> optionalEmailType = findEmailType(emailDTO.getTypeId());
        EmailType emailType = optionalEmailType.get();

        Optional<Person> optionalPerson = findPerson(emailDTO.getPersonId());
        Person person = optionalPerson.get();

        Email email = new Email(emailDTO.getValue(), person, emailType);
        emailRepository.save(email);

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(email);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        return apiResponse;
    }

    public APIResponse editEmail(Long id, EmailDTO emailDTO) {
        validateEmail(emailDTO);

        APIResponse apiResponse = new APIResponse();
        Optional<Email> optionalEmail = findEmail(id);
        Email email = optionalEmail.get();

        Optional<EmailType> optionalEmailType = findEmailType(emailDTO.getTypeId());
        EmailType emailType = optionalEmailType.get();
        Optional<Person> optionalPerson = findPerson(emailDTO.getPersonId());
        Person person = optionalPerson.get();

        email.setPerson(person);
        email.setType(emailType);
        email.setValue(emailDTO.getValue());
        emailRepository.save(email);

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(email);
        apiResponse.setMessage(MessagesResponse.editSuccess);
        return apiResponse;
    }

    public APIResponse deleteEmail(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Email> optionalEmail = findEmail(id);

        Email existingEmail = optionalEmail.get();
        existingEmail.setDeletedAt(LocalDateTime.now());
        emailRepository.save(existingEmail);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingEmail);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }
    public void createEmail(EmailDTO emailDTO){
        validateEmail(emailDTO);
        Optional<EmailType> optionalEmailType = findEmailType(emailDTO.getTypeId());
        Optional<Person> optionalPerson = findPerson(emailDTO.getPersonId());
        if (emailDTO.getId() != null) {
            Optional<Email> optionalEmail = emailRepository.findByIdAndDeletedAtIsNull(emailDTO.getId());
            if (optionalEmail.isPresent()) {
                Email existingEmail = optionalEmail.get();
                existingEmail.setValue(emailDTO.getValue());
                existingEmail.setType(optionalEmailType.get());
                existingEmail.setPerson(optionalPerson.get());
                emailRepository.save(existingEmail);
                return;
            }
        }

        Email newEmail = new Email();
        newEmail.setValue(emailDTO.getValue());
        newEmail.setType(optionalEmailType.get());
        newEmail.setPerson(optionalPerson.get());
        emailRepository.save(newEmail);
    }

    public APIResponse getEmail(Long id) {
        Optional<Email> optionalEmail = findEmail(id);

        APIResponse apiResponse = new APIResponse();
        Email existingEmail = optionalEmail.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingEmail);
        return apiResponse;
    }

    public void validateEmail(EmailDTO emailDTO) {
        if (Stream.of(emailDTO)
                .map(EmailDTO::getValue)
                .anyMatch(value -> value == null || value.isBlank() || value.length() > 45)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(emailDTO)
                .map(EmailDTO::getPersonId)
                .anyMatch(personId -> personId == null || String.valueOf(personId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(emailDTO)
                .map(EmailDTO::getTypeId)
                .anyMatch(typeId -> typeId == null || String.valueOf(typeId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Optional<Email> findEmail(Long id) {
        Optional<Email> optionalEmail = emailRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalEmail.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return optionalEmail;
    }

    public Optional<EmailType> findEmailType(Long id) {
        Optional<EmailType> optionalEmailType = emailTypeRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalEmailType.isPresent()) {
            throw new NotFoundException(MessagesResponse.emailTypeNotFound);
        }
        return optionalEmailType;
    }

    public Optional<Person> findPerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new NotFoundException(MessagesResponse.personNotFund);
        }
        return optionalPerson;
    }
}
