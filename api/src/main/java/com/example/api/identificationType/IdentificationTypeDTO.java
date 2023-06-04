package com.example.api.identificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentificationTypeDTO {
    private String name;
    public IdentificationTypeDTO(){}

    public IdentificationTypeDTO(String name) {
        this.name = name;
    }
}
