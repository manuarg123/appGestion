package com.example.api.phone;

import com.example.api.phoneType.PhoneType;
import com.example.api.phoneType.PhoneTypeDTO;
import com.example.api.phoneType.PhoneTypeFormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class PhoneFormDTO {
    private Long id;
    private String number;
    private PhoneTypeFormDTO type;
}
