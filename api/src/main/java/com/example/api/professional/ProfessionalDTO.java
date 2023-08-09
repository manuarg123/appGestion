package com.example.api.professional;

import com.example.api.clinicHistory.ClinicHistory;
import com.example.api.medicalCenter.MedicalCenterDTO;
import com.example.api.realPerson.RealPersonDTO;
import com.example.api.speciality.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProfessionalDTO extends RealPersonDTO {
    private String mp;
    private List<MedicalCenterDTO> medicalCenters;
    private Speciality speciality;
    private List<ClinicHistory> clinicHistories;
}
