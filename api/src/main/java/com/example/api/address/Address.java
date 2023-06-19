package com.example.api.address;

import com.example.api.AuditableEntity;
import com.example.api.location.Location;
import com.example.api.person.Person;
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
public class Address extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String street;

    @Column(nullable = true, length = 255)
    private String section;

    @Column(nullable = true, length = 255)
    private String number;

    @Column(nullable = true, length = 255)
    private String building;

    @Column(nullable = true, length = 255)
    private String floor;

    @Column(nullable = true, length = 255)
    private String apartment;

    @Column(nullable = true, length = 255)
    private String zip;

    @Column(nullable = true, length = 255)
    private String complete_address;

    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    @JsonBackReference
    private Person person;

    @ManyToOne
    @JoinColumn(name="location_id", nullable = true)
    private Location location;

    @ManyToOne
    @JoinColumn(name="province_id", nullable = true)
    private Province province;
}
