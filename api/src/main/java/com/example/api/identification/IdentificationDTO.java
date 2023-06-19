package com.example.api.identification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentificationDTO {
    private String number;
    private Long personId;
    private Long typeId;

    public IdentificationDTO() {
    }

    public IdentificationDTO(String number, Long personId, Long typeId) {
        this.number = number;
        this.personId = personId;
        this.typeId = typeId;
    }
}