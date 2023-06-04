package com.example.api.rol;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    HashMap<String , Object> data;
    private final RolRepository rolRepository;
    @Autowired
    public RolService(RolRepository rolRepository){this.rolRepository = rolRepository;}

    public List<Rol> getRoles() {
        return this.rolRepository.findByDeletedAtIsNull();
    }

    public APIResponse newRol(RolDTO rolDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<Rol> res = rolRepository.findRolByName(rolDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }
        Rol rol = new Rol();
        rol.setName(rolDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        rolRepository.save(rol);
        data.put("data", rol);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editRol(Long id, RolDTO rolDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Rol> optionalRol = rolRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalRol.isPresent()) {
            Rol existingRol = optionalRol.get();
            existingRol.setName(rolDTO.getName());

            rolRepository.save(existingRol);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingRol);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteRol(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.rolRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Rol> optionalRol = rolRepository.findById(id);
        Rol existingRol = optionalRol.get();
        existingRol.setDeletedAt(LocalDateTime.now());

        rolRepository.save(existingRol);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
