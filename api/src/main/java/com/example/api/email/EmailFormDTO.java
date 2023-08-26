package com.example.api.email;

import com.example.api.emalType.EmailTypeFormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailFormDTO {
    private Long id;
    private String value;
    private EmailTypeFormDTO type;
}
