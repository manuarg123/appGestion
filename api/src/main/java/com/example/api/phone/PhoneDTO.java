package com.example.api.phone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDTO {
    private String value;
    private Long personId;
    private Long typeId;

    public PhoneDTO() {
    }

    public PhoneDTO(String value, Long personId, Long typeId) {
        this.value = value;
        this.personId = personId;
        this.typeId = typeId;
    }
}
