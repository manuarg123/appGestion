package com.example.api.paymentType;

import com.example.api.AuditableEntity;
import com.example.api.payoutConcept.PayoutConcept;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class PaymentType extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public PaymentType(){}

    public PaymentType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PaymentType(String name) {
        this.name = name;
    }
}
