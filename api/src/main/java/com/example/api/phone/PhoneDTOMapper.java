package com.example.api.phone;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PhoneDTOMapper implements Function<Phone, PhoneDTO> {

    @Override
    public PhoneDTO apply(Phone phone) {
        return new PhoneDTO(
                phone.getId(),
                phone.getNumber(),
                phone.getPerson().getId(),
                phone.getType().getId()
        );
    }
}
