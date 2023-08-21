package com.example.api.identification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class IdentificationDTOMapper implements Function<Identification, IdentificationDTO> {
    @Override
    public IdentificationDTO apply(Identification identification) {
        return new IdentificationDTO(
                identification.getId(),
                identification.getNumber(),
                identification.getType().getId(),
                identification.getPerson().getId()
        );
    }
}
