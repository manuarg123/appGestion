package com.example.api.identification;

import com.example.api.identificationType.IdentificationTypeFormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class IdentificationFormDTO {
    private Long id;
    private String number;
    private IdentificationTypeFormDTO type;
}
