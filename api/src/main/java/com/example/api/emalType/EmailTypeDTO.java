package com.example.api.emalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTypeDTO {
    private String name;

    public EmailTypeDTO() {
    }

    public EmailTypeDTO(String name) {
        this.name = name;
    }
}
