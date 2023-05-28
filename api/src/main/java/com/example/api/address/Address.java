package com.example.api.address;

import com.example.api.AuditableEntity;
import com.example.api.location.Location;
import com.example.api.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
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
    private Person person;

    @ManyToOne
    @JoinColumn(name="location_id", nullable = true)
    private Location location;

    public Address() {
    }

    public Address(Long id, String street, String section, String number, String building, String floor, String apartment, String zip, String complete_address, Person person, Location location) {
        this.id = id;
        this.street = street;
        this.section = section;
        this.number = number;
        this.building = building;
        this.floor = floor;
        this.apartment = apartment;
        this.zip = zip;
        this.complete_address = complete_address;
        this.person = person;
        this.location = location;
    }

    public Address(String street, String section, String number, String building, String floor, String apartment, String zip, String complete_address, Person person, Location location) {
        this.street = street;
        this.section = section;
        this.number = number;
        this.building = building;
        this.floor = floor;
        this.apartment = apartment;
        this.zip = zip;
        this.complete_address = complete_address;
        this.person = person;
        this.location = location;
    }
}
