package com.example.api.paymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTypeDTO {

    @NotNull(message = "Name cannot be null")
    @Size(max = 55, message = "Name cannot be exceed 55 characters")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public PaymentTypeDTO() {
    }

    public PaymentTypeDTO(String name) {
        this.name = name;
    }
}
