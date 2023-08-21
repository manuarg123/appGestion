package com.example.api.professional;

import com.example.api.address.AddressDTO;
import com.example.api.email.EmailDTO;
import com.example.api.identification.IdentificationDTO;
import com.example.api.phone.PhoneDTO;
import com.example.api.realPerson.RealPersonDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProfessionalDTO extends RealPersonDTO {

    @Size(max = 144, message = "mp cannot exceed 144 characters")
    private String mp;

    private List<Long> medicalCenterIds;

    @NotNull(message = "Specialty cannot be null")
    private Long specialityId;

    public ProfessionalDTO(Long genderId, String firstName, String secondName, String lastName, String secondLastName, LocalDate birthday, List<AddressDTO> addressDTOS, List<PhoneDTO> phoneDTOS, List<EmailDTO> emailDTOS, List<IdentificationDTO> identificationDTOS, String mp, List<Long> medicalCenterIds, @NotNull(message = "Specialty cannot be null") Long specialityId) {
        super(genderId, firstName, secondName, lastName, secondLastName, birthday, addressDTOS, phoneDTOS, emailDTOS, identificationDTOS);
        this.mp = mp;
        this.medicalCenterIds = medicalCenterIds;
        this.specialityId = specialityId;
    }

    public ProfessionalDTO() {
        super();
    }
}
