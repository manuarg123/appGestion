package com.example.api.professional;

import com.example.api.realPerson.RealPersonDTO;
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
public class ProfessionalDTO extends RealPersonDTO {

    @Size(max = 144, message = "mp cannot exceed 144 characters")
    private String mp;

    private List<Long> medicalCenterIds;

    @NotNull(message = "Specialty cannot be null")
    private Long specialityId;
}
