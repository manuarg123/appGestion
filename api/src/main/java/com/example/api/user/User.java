package com.example.api.user;

import com.example.api.AuditableEntity;
import com.example.api.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@Entity
@Table
public class User extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = false, length = 144)
    private String password;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    public User(){
    }

    public User(Long id, String username, String password, Person person) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.person = person;
    }

    public User(String username, String password, Person person) {
        this.username = username;
        this.password = password;
        this.person = person;
    }
}
