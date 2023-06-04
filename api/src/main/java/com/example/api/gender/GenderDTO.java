package com.example.api.gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenderDTO {
    private String name;

    public GenderDTO() {
    }

    public GenderDTO(String name) {
        this.name = name;
    }
}
