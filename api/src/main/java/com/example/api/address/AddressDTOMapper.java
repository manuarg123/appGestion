package com.example.api.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AddressDTOMapper implements Function<Address, AddressDTO> {
    @Override
    public AddressDTO apply(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getSection(),
                address.getNumber(),
                address.getFloor(),
                address.getApartment(),
                address.getZip(),
                address.getComplete_address(),
                address.getPerson().getId(),
                address.getLocation().getId(),
                address.getProvince().getId()
        );
    }
}
