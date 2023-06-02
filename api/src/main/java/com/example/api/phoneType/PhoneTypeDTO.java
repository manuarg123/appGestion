package com.example.api.phoneType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneTypeDTO {
    private String name;

    public PhoneTypeDTO() {
    }

    public PhoneTypeDTO(String name) {
        this.name = name;
    }
}
