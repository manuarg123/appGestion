package com.example.api.email;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmailDTOMapper implements Function<Email, EmailDTO> {

    @Override
    public EmailDTO apply(Email email) {
        return new EmailDTO(
                email.getId(),
                email.getValue(),
                email.getPerson().getId(),
                email.getType().getId()
        );
    }
}
