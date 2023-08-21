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

    public PersonDTO() {
    }

    public PersonDTO(List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS) {
        this.addresses = addressDTOS;
        this.phones = phoneDTOS;
        this.emails = emailDTOS;
        this.identifications = identificationDTOS;
    }
}
