package com.example.api.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AddressFormDTOMapper implements Function<Address, AddressFormDTO> {

    @Override
    public AddressFormDTO apply(Address address) {
        return new AddressFormDTO(
                address.getId(),
                address.getStreet(),
                address.getSection(),
                address.getNumber(),
                address.getFloor(),
                address.getApartment(),
                address.getZip(),
                address.getLocation().getId(),
                address.getProvince().getId(),
                address.getLocation().getName(),
                address.getProvince().getName()
        );
    }
}
