package com.example.api.logout;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.utils.TokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LogoutController {
    @Autowired
    private LogoutService logoutService;
    @PostMapping(path="/logout")
    public ResponseEntity<APIResponse> logout(){
        try {
            APIResponse apiResponse = logoutService.logout();
            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);
        } catch (Exception e) {
            APIResponse errorResponse = new APIResponse();
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setData(MessagesResponse.logoutFailed);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
