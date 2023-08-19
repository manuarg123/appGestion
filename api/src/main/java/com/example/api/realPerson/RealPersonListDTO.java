package com.example.api.realPerson;

import com.example.api.person.PersonListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RealPersonListDTO extends PersonListDTO {
    private String gender;

    public RealPersonListDTO(Long id, String fullName, String firstPhoneNumber, String firstEmailValue, String genderName) {
        super(id, fullName, firstPhoneNumber,firstEmailValue);
        this.gender = genderName;
    }
}