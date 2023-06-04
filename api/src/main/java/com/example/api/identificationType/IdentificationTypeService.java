package com.example.api.identificationType;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class IdentificationTypeService {
    HashMap<String, Object> data;
    private final IdentificationTypeRepository identificationTypeRepository;
    @Autowired
    IdentificationTypeService(IdentificationTypeRepository identificationTypeRepository){this.identificationTypeRepository = identificationTypeRepository;}

    public List<IdentificationType> getIdentificationTypes() {
        return this.identificationTypeRepository.findByDeletedAtIsNull();
    }

    public APIResponse newIdentificationType(IdentificationTypeDTO identificationTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<IdentificationType> res = identificationTypeRepository.findIdentificationTypeByName(identificationTypeDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }

        IdentificationType identificationType = new IdentificationType();
        identificationType.setName(identificationTypeDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        identificationTypeRepository.save(identificationType);
        data.put("data", identificationType);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editIdentificationType(Long id, IdentificationTypeDTO identificationTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalIdentificationType.isPresent()){
            IdentificationType existingIdentificationType = optionalIdentificationType.get();
            existingIdentificationType.setName(identificationTypeDTO.getName());

            identificationTypeRepository.save(existingIdentificationType);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingIdentificationType);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteIdentificationType(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.identificationTypeRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<IdentificationType> optionalIdentificationType = identificationTypeRepository.findById(id);
        IdentificationType existingIdentificationType = optionalIdentificationType.get();
        existingIdentificationType.setDeletedAt(LocalDateTime.now());

        identificationTypeRepository.save(existingIdentificationType);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
