package com.example.api.user;

import com.example.api.common.MessagesResponse;
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
            data.put("message", MessagesResponse.recordNameExists);
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        data.put("message", MessagesResponse.addSuccess);
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
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingUser);

            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        boolean exists = this.userRepository.existsById(id);
        data = new HashMap<>();

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);

            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        Optional<User> optionalUser = userRepository.findById(id);
        User existingUser = optionalUser.get();
        existingUser.setDeletedAt(LocalDateTime.now());

        userRepository.save(existingUser);
        data.put("message", MessagesResponse.deleteSuccess);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
