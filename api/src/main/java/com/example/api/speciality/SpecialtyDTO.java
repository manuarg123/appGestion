package com.example.api.speciality;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialtyDTO {
    private String name;

    public SpecialtyDTO() {}

    public SpecialtyDTO(String name) {
        this.name = name;
    }
}
