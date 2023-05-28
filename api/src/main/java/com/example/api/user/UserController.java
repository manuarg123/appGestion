package com.example.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){this.userService = userService;}

    @GetMapping
    public List<User> getUsers(){return userService.getUsers();}

    @PostMapping(path="/new")
    public ResponseEntity<Object>addUser(@RequestBody User user){
        return this.userService.newUser(user);
    }

    @PutMapping(path="/edit/{userId}")
    public ResponseEntity<Object>editUser(@PathVariable("userId") Long id, @RequestBody User user){
        return this.userService.editUser(id, user);
    }

    @DeleteMapping(path="/delete/{userId}")
    public ResponseEntity<Object>deleteUser(@PathVariable("userId") Long id){
        return this.userService.deleteUser(id);
    }
}
