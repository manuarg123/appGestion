package com.example.api.emergencyContact;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmergencyContactDTOMapper implements Function<EmergencyContact, EmergencyContactDTO> {
    @Override
    public EmergencyContactDTO apply(EmergencyContact emergencyContact) {
        return new EmergencyContactDTO(
                emergencyContact.getId(),
                emergencyContact.getName(),
                emergencyContact.getPhoneNumber(),
                emergencyContact.getPatient().getId()
        );
    }
}
