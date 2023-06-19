package com.example.api.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
    private String value;
    private Long personId;
    private Long typeId;

    public EmailDTO() {
    }

    public EmailDTO(String value, Long personId, Long typeId) {
        this.value = value;
        this.personId = personId;
        this.typeId = typeId;
    }
}