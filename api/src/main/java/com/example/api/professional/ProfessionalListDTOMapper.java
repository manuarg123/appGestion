package com.example.api.professional;

import com.example.api.email.Email;
import com.example.api.gender.Gender;
import com.example.api.phone.Phone;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProfessionalListDTOMapper implements Function<Professional, ProfessionalListDTO> {

    @Override
    public ProfessionalListDTO apply(Professional professional) {
        String firstPhoneNumber = null;
        if (professional.getPhones() != null && !professional.getPhones().isEmpty()) {
            Phone firstPhone = professional.getPhones().get(0);
            if (firstPhone != null) {
                firstPhoneNumber = firstPhone.getNumber();
            }
        }

        String firstEmailValue = null;
        if (professional.getEmails() != null && !professional.getEmails().isEmpty()) {
            Email firstEmail = professional.getEmails().get(0);
            if (firstEmail != null) {
                firstEmailValue = firstEmail.getValue();
            }
        }

        String genderName = null;
        if (professional.getGender() != null) {
            genderName = professional.getGender().getName();
        }

        String specialtyName = null;
        if (professional.getSpeciality() != null) {
            specialtyName = professional.getSpeciality().getName();
        }
        return new ProfessionalListDTO(
                professional.getId(),
                professional.getFullName(),
                firstPhoneNumber,
                firstEmailValue,
                genderName,
                specialtyName
        );
    }
}
