package com.example.api.logout;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.utils.TokenHolder;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    public APIResponse logout() {
        APIResponse apiResponse = new APIResponse();

        if (!TokenHolder.accessToken.equals("")){
            TokenHolder.accessToken = "";
            apiResponse.setData(MessagesResponse.logoutSuccess);
            return apiResponse;
        } else {
            apiResponse.setData(MessagesResponse.logoutFailed);
            return apiResponse;
        }

    }
}
