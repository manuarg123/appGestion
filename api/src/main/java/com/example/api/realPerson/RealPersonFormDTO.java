package com.example.api.realPerson;

import com.example.api.address.AddressFormDTO;
import com.example.api.email.EmailFormDTO;
import com.example.api.identification.IdentificationFormDTO;
import com.example.api.person.PersonFormDTO;
import com.example.api.phone.PhoneFormDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RealPersonFormDTO extends PersonFormDTO {
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private LocalDate birthday;
    private Long genderId;
    private String genderName;

    public RealPersonFormDTO() {
    }

    public RealPersonFormDTO(List<AddressFormDTO> addresses, List<PhoneFormDTO> phones, List<EmailFormDTO> emails, List<IdentificationFormDTO> identifications, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, Long genderId, String genderName) {
        super(addresses, phones, emails, identifications);
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthday = birthday;
        this.genderId = genderId;
        this.genderName = genderName;
    }

}
