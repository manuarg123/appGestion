package com.example.api.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolDTO {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max=45, message = "Name cannot be exceed 45 characters")
    private String name;

    public RolDTO() {
    }

    public RolDTO(String name) {
        this.name = name;
    }
}
