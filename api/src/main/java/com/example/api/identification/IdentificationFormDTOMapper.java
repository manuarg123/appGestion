package com.example.api.identification;

import com.example.api.identificationType.IdentificationTypeFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class IdentificationFormDTOMapper implements Function<Identification, IdentificationFormDTO> {

    @Override
    public IdentificationFormDTO apply(Identification identification) {
        IdentificationTypeFormDTO identificationTypeFormDTO = new IdentificationTypeFormDTO();
        identificationTypeFormDTO.setId(identification.getType().getId());
        identificationTypeFormDTO.setName(identification.getType().getName());
        return new IdentificationFormDTO(
                identification.getId(),
                identification.getNumber(),
                identificationTypeFormDTO
        );
    }
}
