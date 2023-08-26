package com.example.api.phone;

import com.example.api.phoneType.PhoneTypeFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class PhoneDTOMapper implements Function<Phone, PhoneFormDTO> {

    @Override
    public PhoneFormDTO apply(Phone phone) {
        PhoneTypeFormDTO phoneTypeFormDTO = new PhoneTypeFormDTO();
        phoneTypeFormDTO.setId(phone.getType().getId());
        phoneTypeFormDTO.setName(phone.getType().getName());
        return new PhoneFormDTO(
                phone.getId(),
                phone.getNumber(),
                phoneTypeFormDTO
        );
    }
}
