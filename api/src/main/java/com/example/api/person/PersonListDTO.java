package com.example.api.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonListDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
}
