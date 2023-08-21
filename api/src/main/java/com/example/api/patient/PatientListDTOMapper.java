package com.example.api.patient;

import com.example.api.email.Email;
import com.example.api.phone.Phone;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PatientListDTOMapper implements Function<Patient, PatientListDTO> {

    @Override
    public PatientListDTO apply(Patient patient) {

        String firstPhoneNumber = null;
        if (patient.getPhones() != null && !patient.getPhones().isEmpty()) {
            Phone firstPhone = patient.getPhones().get(0);
            if (firstPhone != null) {
                firstPhoneNumber = firstPhone.getNumber();
            }
        }

        String firstEmailValue = null;
        if (patient.getEmails() != null && !patient.getEmails().isEmpty()) {
            Email firstEmail = patient.getEmails().get(0);
            if (firstEmail != null) {
                firstEmailValue = firstEmail.getValue();
            }
        }

        String genderName = null;
        if (patient.getGender() != null) {
            genderName = patient.getGender().getName();
        }

        return new PatientListDTO(
                patient.getId(),
                patient.getFullName(),
                firstPhoneNumber,
                firstEmailValue,
                genderName,
                patient.getOccupation()
        );
    }
}
