package com.example.api.realPerson;

import com.example.api.person.PersonDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RealPersonDTO extends PersonDTO {
    @Size(max = 144, message = "fistName cannot exceed 144 characters")
    private String firstName;

    @Size(max = 144, message = "secondName cannot exceed 144 characters")
    private String secondName;

    @Size(max = 144, message = "lastName cannot exceed 144 characters")
    private String lastName;

    @Size(max = 144, message = "secondLastName cannot exceed 144 characters")
    private String secondLastName;

    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Long genderId;
}