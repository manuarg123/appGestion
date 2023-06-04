package com.example.api.rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolDTO {
    private String name;

    public RolDTO() {
    }

    public RolDTO(String name) {
        this.name = name;
    }
}
