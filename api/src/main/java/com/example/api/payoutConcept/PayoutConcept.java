package com.example.api.payoutConcept;

import com.example.api.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class PayoutConcept extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public PayoutConcept(){
    }
    public PayoutConcept(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PayoutConcept(String name) {
        this.name = name;
    }
}
