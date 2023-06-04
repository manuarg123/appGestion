package com.example.api.payoutConcept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayoutConceptDTO {
    private String name;

    public PayoutConceptDTO() {
    }

    public PayoutConceptDTO(String name) {
        this.name = name;
    }
}
