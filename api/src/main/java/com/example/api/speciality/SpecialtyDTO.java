package com.example.api.speciality;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialtyDTO {
    @NotNull(message="Name cannot be null")
    @Size(max=144, message="Name cannot be exceed 144 characters")
    @NotBlank(message="Name cannot be blank")
    private String name;

    public SpecialtyDTO() {}

    public SpecialtyDTO(String name) {
        this.name = name;
    }
}
