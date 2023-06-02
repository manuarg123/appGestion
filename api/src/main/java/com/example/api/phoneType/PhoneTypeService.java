package com.example.api.phoneType;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneTypeService {
    HashMap<String , Object> data;

    private final PhoneTypeRepository phoneTypeRepository;
    @Autowired
    public PhoneTypeService(PhoneTypeRepository phoneTypeRepository){this.phoneTypeRepository = phoneTypeRepository;}
    public List<PhoneType> getPhoneTypes() {
        return this.phoneTypeRepository.findByDeletedAtIsNull();
    }

    public APIResponse newPhoneType(PhoneTypeDTO phoneTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<PhoneType> res = phoneTypeRepository.findPhoneTypeByName(phoneTypeDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", "Ya existe un registro con ese nombre");
            apiResponse.setData(data);
            return apiResponse;
        }
        PhoneType phoneType = new PhoneType();
        phoneType.setName(phoneTypeDTO.getName());
        data.put("message", "Se guardo el registro con éxito");

        phoneTypeRepository.save(phoneType);
        data.put("data", phoneType);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editPhoneType(Long id ,PhoneTypeDTO phoneTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findByIdAndDeletedAtIsNull(id);

        if(optionalPhoneType.isPresent()){
            PhoneType existingPhoneType = optionalPhoneType.get();
            existingPhoneType.setName(phoneTypeDTO.getName());

            phoneTypeRepository.save(existingPhoneType);
            data.put("message", "Se actualizo el registro con éxito");
            data.put("data", existingPhoneType);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", "Ocurrio un error al actualizar");
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deletePhoneType(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.phoneTypeRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", "No existe el registro");
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<PhoneType> optionalPhoneType = phoneTypeRepository.findById(id);
        PhoneType existingPhoneType = optionalPhoneType.get();
        existingPhoneType.setDeletedAt(LocalDateTime.now());

        phoneTypeRepository.save(existingPhoneType);
        data.put("message", "registro eliminado");
        apiResponse.setData(data);
        return apiResponse;
    }
}
