package com.example.api.realPerson;

import com.example.api.address.AddressDTO;
import com.example.api.email.EmailDTO;
import com.example.api.identification.IdentificationDTO;
import com.example.api.person.PersonDTO;
import com.example.api.phone.PhoneDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RealPersonDTO extends PersonDTO {
    @Size(max = 144, message = "fistName cannot exceed 144 characters")
    private String firstName;

    @Size(max = 144, message = "secondName cannot exceed 144 characters")
    private String secondName;

    @Size(max = 144, message = "lastName cannot exceed 144 characters")
    private String lastName;

    @Size(max = 144, message = "secondLastName cannot exceed 144 characters")
    private String secondLastName;

    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Long genderId;

    public RealPersonDTO(List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS) {
        super(addressDTOS, phoneDTOS, emailDTOS, identificationDTOS);
    }

    public RealPersonDTO(Long genderId, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS) {
        super(addressDTOS, phoneDTOS, emailDTOS, identificationDTOS);
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthday = birthday;
        this.genderId = genderId;
    }

    public RealPersonDTO(){
        super();
    }
}