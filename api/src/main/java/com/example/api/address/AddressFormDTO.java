package com.example.api.address;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressFormDTO {
    private Long id;
    private String street;
    private String section;
    private String number;
    private String floor;
    private String apartment;
    private String zip;
    private Long locationId;
    private Long provinceId;
    private String locationName;
    private String provinceName;
}
