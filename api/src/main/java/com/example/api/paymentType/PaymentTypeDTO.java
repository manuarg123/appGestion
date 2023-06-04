package com.example.api.paymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTypeDTO {
    private String name;

    public PaymentTypeDTO() {
    }

    public PaymentTypeDTO(String name) {
        this.name = name;
    }
}
