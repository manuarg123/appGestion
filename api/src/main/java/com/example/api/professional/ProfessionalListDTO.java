package com.example.api.professional;

import com.example.api.realPerson.RealPersonListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfessionalListDTO extends RealPersonListDTO {
    private String specialty;

    public ProfessionalListDTO(Long id, String fullName, String firstPhoneNumber, String firstEmailValue, String genderName, String specialtyName) {
        super(id, fullName, firstPhoneNumber, firstEmailValue, genderName);
        this.specialty = specialtyName;
    }
}
