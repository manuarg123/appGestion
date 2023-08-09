package com.example.api.realPerson;

import com.example.api.person.PersonDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RealPersonDTO extends PersonDTO {
    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate birthday;
    private Long genderId;
}
