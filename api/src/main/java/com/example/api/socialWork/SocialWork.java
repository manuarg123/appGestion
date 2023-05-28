package com.example.api.socialWork;

import com.example.api.address.Address;
import com.example.api.email.Email;
import com.example.api.person.Person;
import com.example.api.phone.Phone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="social_work")
@Getter
@Setter
public class SocialWork extends Person {
    @Column(nullable = false, length = 144)
    private String name;

    public SocialWork(){}

    public SocialWork(Long id, String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, String name) {
        super(id, fullName, addresses, phones, emails);
        this.name = name;
    }

    public SocialWork(String fullName, List<Address> addresses, List<Phone> phones, List<Email> emails, String name) {
        super(fullName, addresses, phones, emails);
        this.name = name;
    }
}
