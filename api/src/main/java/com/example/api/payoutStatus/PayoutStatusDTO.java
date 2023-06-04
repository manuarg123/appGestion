package com.example.api.payoutStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutStatusDTO {
    private String name;

    public PayoutStatusDTO() {
    }

    public PayoutStatusDTO(String name) {
        this.name = name;
    }
}
