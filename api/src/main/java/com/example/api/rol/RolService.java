package com.example.api.rol;

import com.example.api.common.*;
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
public class RolService {
    HashMap<String , Object> data;
    private final RolRepository rolRepository;
    @Autowired
    public RolService(RolRepository rolRepository){this.rolRepository = rolRepository;}

    public List<Rol> getRoles() {
        return this.rolRepository.findByDeletedAtIsNull();
    }

    public APIResponse newRol(RolDTO rolDTO) {
        if (Stream.of(rolDTO)
                .map(RolDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<Rol> res = rolRepository.findRolByName(rolDTO.getName());

        if (res.isPresent()) {
            Rol existingRol = res.get();
            if (existingRol.getDeletedAt() == null){
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        Rol rol = new Rol();
        rol.setName(rolDTO.getName());
        rolRepository.save(rol);

        apiResponse.setData(rol);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editRol(Long id, RolDTO rolDTO) {
        if (Stream.of(rolDTO)
                .map(RolDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<Rol> optionalRol = rolRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalRol.isPresent()) {
            Optional<Rol> res = rolRepository.findRolByName(rolDTO.getName());

            if (res.isPresent()) {
                Rol existingRol = res.get();
                if (existingRol.getDeletedAt() == null){
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            Rol existingRol = optionalRol.get();
            existingRol.setName(rolDTO.getName());
            rolRepository.save(existingRol);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingRol);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deleteRol(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Rol> optionalRol = rolRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalRol.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        Rol existingRol = optionalRol.get();
        existingRol.setDeletedAt(LocalDateTime.now());
        rolRepository.save(existingRol);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingRol);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getRol(Long id) {
        Optional<Rol> optionalRol = rolRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalRol.isPresent()){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        Rol existingRol = optionalRol.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingRol);
        return apiResponse;
    }
}
