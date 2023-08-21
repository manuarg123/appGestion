package com.example.api.patient;

import com.example.api.realPerson.RealPersonListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientListDTO extends RealPersonListDTO {
    private String occupation;

    public PatientListDTO(Long id, String fullName, String firstPhoneNumber, String firstEmailValue, String genderName, String occupation) {
        super(id, fullName, firstPhoneNumber, firstEmailValue, genderName);
        this.occupation = occupation;
    }
}
