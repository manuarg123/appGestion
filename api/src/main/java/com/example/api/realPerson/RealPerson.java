package com.example.api.realPerson;

import com.example.api.address.Address;
import com.example.api.email.Email;
import com.example.api.gender.Gender;
import com.example.api.person.Person;
import com.example.api.phone.Phone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "real_person")
@Getter
@Setter
public class RealPerson extends Person {

    @Column(name="first_name", nullable = true, length = 144)
    private String firstName;

    @Column(name="second_name", nullable = true, length = 144)
    private String secondName;

    @Column(name="last_name", nullable = true, length = 144)
    private String lastName;

    @Column(name="second_last_name", nullable = true, length = 144)
    private String secondLastName;

    @Column(nullable = true)
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name="gender_id", nullable = false)
    private Gender gender;

    public RealPerson(){

    }
    public RealPerson(Long id, String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Gender gender) {
        super(id, fullName, addresses, phones, emails);
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthday = birthday;
        this.gender = gender;
    }

    public RealPerson(String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Gender gender) {
        super(fullName, addresses, phones, emails);
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthday = birthday;
        this.gender = gender;
    }
}
