    package com.example.api.login;

    import com.example.api.common.APIResponse;
    import com.example.api.utils.JwtUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.HashMap;

    @RestController
    public class LoginController {

        @Autowired
        private LoginService loginService;
        @Autowired
        private JwtUtils jwtUtils;
        @PostMapping(path="/signup")
        public ResponseEntity<APIResponse> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){

            APIResponse apiResponse = loginService.signup(signUpRequestDTO);

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);
        }

        @PostMapping(path="/login")
        public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequestDTO){
            APIResponse apiResponse = loginService.login(loginRequestDTO);

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);
        }

        @GetMapping("/privateApi")
        public ResponseEntity<APIResponse> privateApi(@RequestHeader(value = "authorization", defaultValue = "")String auth) throws Exception {
            APIResponse apiResponse = new APIResponse();

            jwtUtils.verify(auth);

            apiResponse.setData("This is a private Api");
            return ResponseEntity.status(apiResponse.getStatus())
                    .body(apiResponse);
        }
    }
