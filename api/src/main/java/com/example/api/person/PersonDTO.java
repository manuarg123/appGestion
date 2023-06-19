package com.example.api.person;

import com.example.api.address.AddressDTO;
import com.example.api.email.EmailDTO;
import com.example.api.identification.IdentificationDTO;
import com.example.api.phone.PhoneDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDTO {
    private List<AddressDTO> addresses;
    private List<PhoneDTO> phones;
    private List<EmailDTO> emails;
    private List<IdentificationDTO> identifications;
}
