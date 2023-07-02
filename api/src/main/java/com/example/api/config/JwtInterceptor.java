package com.example.api.config;

import com.example.api.common.AccessDeniedException;
import com.example.api.dto.RequestMeta;
import com.example.api.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils;

    private RequestMeta requestMeta;

    public JwtInterceptor (RequestMeta requestMeta, JwtUtils jwtUtils){
        this.requestMeta = requestMeta;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            ServletServerHttpRequest httpRequest = new ServletServerHttpRequest(request);
            String auth = httpRequest.getHeaders().getFirst("authorization");
            
            //Exceptua esas rutas para que no verifiquen el token
            if (!(request.getRequestURI().contains("login") || request.getRequestURI().contains("signup"))) {
                // Verifica si el encabezado de autorización está presente y tiene el formato adecuado
                if (auth != null && auth.startsWith("Bearer ")) {
                    String token = auth.substring(7); // Elimina el prefijo "Bearer " para obtener el token

                    Claims claims = jwtUtils.verify(token);

                    requestMeta.setUsername(claims.get("username").toString());
                    requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
                } else {
                    // Lanza una excepción si el token de autorización no está presente o no tiene el formato adecuado
                    throw new AccessDeniedException("Acceso denegado");
                }
            }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
