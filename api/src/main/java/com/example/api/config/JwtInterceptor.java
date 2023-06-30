package com.example.api.config;

import com.example.api.dto.RequestMeta;
import com.example.api.utils.JwtUtils;
import com.example.api.utils.TokenHolder;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    private RequestMeta requestMeta;

    public JwtInterceptor (RequestMeta requestMeta){
        this.requestMeta = requestMeta;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = TokenHolder.accessToken;

        //Exceptua esas rutas para que no verifiquen el token
        if (!(request.getRequestURI().contains("login") || request.getRequestURI().contains("signup"))){
            Claims claims = jwtUtils.verify(auth);

            requestMeta.setUsername(claims.get("username").toString());
            requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}