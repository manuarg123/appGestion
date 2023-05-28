package com.example.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    HashMap<String, Object> data;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){this.userRepository = userRepository;}
    public List<User> getUsers() {return this.userRepository.findByDeletedAtIsNull();}


    public ResponseEntity<Object> newUser(User user) {
        Optional<User> res = userRepository.findUserByUsername(user.getUsername());
        data = new HashMap<>();

        if (res.isPresent() && user.getId() == null){
            data.put("error",true);
            data.put("message", "Ya existe un registro con este nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        data.put("message", "Se guardó con éxito el registro");
        userRepository.save(user);
        data.put("data", user);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findByIdAndDeletedAtIsNull(id);
        data = new HashMap<>();

        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            existingUser.setUsername(user.getUsername());

            userRepository.save(existingUser);
            data.put("message", "Registro actualizado exitosamente");
            data.put("data", existingUser);

            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            data.put("error", true);
            data.put("message", "No se encontro un registro con ese id:" + id);
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        boolean exists = this.userRepository.existsById(id);
        data = new HashMap<>();

        if (!exists){
            data.put("error", true);
            data.put("message", "No existe el registro");

            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        Optional<User> optionalUser = userRepository.findById(id);
        User existingUser = optionalUser.get();
        existingUser.setDeletedAt(LocalDateTime.now());

        userRepository.save(existingUser);
        data.put("message", "Registro eliminado");
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
