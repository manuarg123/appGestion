package com.example.api.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {
    private String username;
    private String password;
    private Long personId;
    private Long rolId;
}
