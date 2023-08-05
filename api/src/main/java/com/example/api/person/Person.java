package com.example.api.person;

import com.example.api.AuditableEntity;
import com.example.api.address.Address;
import com.example.api.email.Email;
import com.example.api.identification.Identification;
import com.example.api.phone.Phone;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="full_name", nullable = true, length = 255)
    private String fullName;

    @OneToMany(mappedBy = "person")
    @JsonManagedReference
    @Where(clause = "deleted_at IS NULL")
    private List<Address> addresses;

    @OneToMany(mappedBy = "person")
    @JsonManagedReference
    @Where(clause = "deleted_at IS NULL")
    private List<Phone> phones;

    @OneToMany(mappedBy = "person")
    @JsonManagedReference
    @Where(clause = "deleted_at IS NULL")
    private List<Email> emails;

    @OneToMany(mappedBy = "person")
    @JsonManagedReference
    @Where(clause = "deleted_at IS NULL")
    private List<Identification> identifications;

    public Person(){
    }

    public Person(Long id, String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, List<Identification> identifications) {
        this.id = id;
        this.fullName = fullName;
        this.addresses = addresses;
        this.phones = phones;
        this.emails = emails;
        this.identifications = identifications;
    }

    public Person(String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, List<Identification> identifications) {
        this.fullName = fullName;
        this.addresses = addresses;
        this.phones = phones;
        this.emails = emails;
        this.identifications = identifications;
    }
}
