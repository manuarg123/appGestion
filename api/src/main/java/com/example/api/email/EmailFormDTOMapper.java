package com.example.api.email;

import com.example.api.emalType.EmailTypeFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class EmailFormDTOMapper implements Function<Email, EmailFormDTO> {

    @Override
    public EmailFormDTO apply(Email email) {
        EmailTypeFormDTO emailTypeFormDTO = new EmailTypeFormDTO();
        emailTypeFormDTO.setId(email.getType().getId());
        emailTypeFormDTO.setName(email.getType().getName());
        return new EmailFormDTO(
                email.getId(),
                email.getValue(),
                emailTypeFormDTO
        );
    }
}
