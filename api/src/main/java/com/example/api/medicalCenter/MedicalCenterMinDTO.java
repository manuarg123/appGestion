package com.example.api.medicalCenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MedicalCenterMinDTO {
    private Long id;
    private String name;
    private String direction;
}
