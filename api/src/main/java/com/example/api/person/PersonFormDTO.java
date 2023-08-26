package com.example.api.person;

import com.example.api.address.AddressFormDTO;
import com.example.api.email.EmailFormDTO;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationFormDTO;
import com.example.api.phone.PhoneFormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
public class PersonFormDTO {
    List<AddressFormDTO> addresses;
    List<PhoneFormDTO> phones;
    List<EmailFormDTO> emails;
    List<IdentificationFormDTO> identifications;

    public PersonFormDTO() {
    }

    public PersonFormDTO(List<AddressFormDTO> addresses, List<PhoneFormDTO> phones, List<EmailFormDTO> emails, List<IdentificationFormDTO> identifications) {
        this.addresses = addresses;
        this.phones = phones;
        this.emails = emails;
        this.identifications = identifications;
    }
}
