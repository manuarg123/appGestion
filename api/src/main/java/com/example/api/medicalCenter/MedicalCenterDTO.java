package com.example.api.medicalCenter;

import com.example.api.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCenterDTO extends PersonDTO {
    private String name;
}