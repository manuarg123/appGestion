package com.example.api.medicalCenter;

import com.example.api.address.AddressDTO;
import com.example.api.email.EmailDTO;
import com.example.api.identification.IdentificationDTO;
import com.example.api.person.PersonDTO;
import com.example.api.phone.PhoneDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCenterDTO extends PersonDTO {
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max=144, message = "Name cannot be exceed 144 characters")
    private String name;
}