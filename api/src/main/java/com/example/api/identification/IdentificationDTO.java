package com.example.api.identification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IdentificationDTO {
    private Long id;

    @Size(max= 45, message = "Value cannot be exceed 55 characters")
    private String value;

    @NotNull(message = "personId cannot be null")
    private Long personId;

    @NotNull(message = "typeId cannot be null")
    private Long typeId;

    public IdentificationDTO() {
    }

    public IdentificationDTO(String value, Long personId, Long typeId) {
        this.value = value;
        this.personId = personId;
        this.typeId = typeId;
    }
}