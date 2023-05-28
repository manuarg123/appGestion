package com.example.api.payoutStatus;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class PayoutStatus extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public PayoutStatus(){}

    public PayoutStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PayoutStatus(String name) {
        this.name = name;
    }
}
