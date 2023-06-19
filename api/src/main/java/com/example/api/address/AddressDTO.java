package com.example.api.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String street;
    private String section;
    private String number;
    private String floor;
    private String apartment;
    private String zip;
    private String complete_address;
    private Long personId;
    private Long locationId;
    private Long provinceId;
}
