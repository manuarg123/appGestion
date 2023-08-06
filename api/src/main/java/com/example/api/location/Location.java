package com.example.api.location;

import com.example.api.AuditableEntity;
import com.example.api.province.Province;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name="province_id", nullable=true)
    @JsonBackReference
    private  Province province;
}
