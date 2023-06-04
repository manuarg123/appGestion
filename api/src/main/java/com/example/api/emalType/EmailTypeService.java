package com.example.api.emalType;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EmailTypeService {
    HashMap<String, Object> data;
    private final EmailTypeRepository emailTypeRepository;

    @Autowired
    EmailTypeService(EmailTypeRepository emailTypeRepository){this.emailTypeRepository = emailTypeRepository;}
    public List<EmailType> getEmailTypes() {return this.emailTypeRepository.findByDeletedAtIsNull();}

    public APIResponse newEmailType(EmailTypeDTO emailTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<EmailType> res = emailTypeRepository.findEmailTypeByName(emailTypeDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }

        EmailType emailType = new EmailType();
        emailType.setName(emailTypeDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        emailTypeRepository.save(emailType);
        data.put("data", emailType);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editEmailType(Long id, EmailTypeDTO emailTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<EmailType> optionalEmailType = emailTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalEmailType.isPresent()){
            EmailType existingEmailType = optionalEmailType.get();
            existingEmailType.setName(emailTypeDTO.getName());

            emailTypeRepository.save(existingEmailType);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingEmailType);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteEmailType(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.emailTypeRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<EmailType> optionalEmailType = emailTypeRepository.findById(id);
        EmailType existingEmailType =  optionalEmailType.get();
        existingEmailType.setDeletedAt(LocalDateTime.now());

        emailTypeRepository.save(existingEmailType);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}