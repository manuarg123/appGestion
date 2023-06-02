package com.example.api.login;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.rol.Rol;
import com.example.api.rol.RolRepository;
import com.example.api.user.User;
import com.example.api.user.UserRepository;
import com.example.api.utils.JwtUtils;
import com.example.api.utils.TokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class LoginService {
    HashMap<String, Object> data;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public APIResponse signup(SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = new APIResponse();
        Optional<Person> person = personRepository.findById(signUpRequestDTO.getPersonId());
        Optional<Rol> rol = rolRepository.findById(signUpRequestDTO.getRolId());
        //Validacion

        //Entidad
        User userEntity = new User();
        userEntity.setUsername(signUpRequestDTO.getUsername());
        userEntity.setPassword(signUpRequestDTO.getPassword());
        if (person.isPresent()){
            userEntity.setPerson(person.get());
        }
        if (rol.isPresent()){
            userEntity.setRol(rol.get());
        }

        userEntity = userRepository.save(userEntity);

        apiResponse.setData(userEntity);
        return apiResponse;
    }

    public APIResponse login(LoginRequestDTO loginRequestDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();
        //Validation
        //Verificacion
        User user = userRepository.findOneByUsernameIgnoreCaseAndPassword(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        //Response
        if(user == null){
            apiResponse.setData(MessagesResponse.loginFailed);
            return apiResponse;
        }
        String token = jwtUtils.generateJwt(user);
        TokenHolder.accessToken = token;
        data.put("message", MessagesResponse.loginSuccess);
        data.put("accessToken", token);
        apiResponse.setData(data);
        return apiResponse;
    }
}
