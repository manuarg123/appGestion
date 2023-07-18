package com.example.api.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @Size(max=255, message = "Cannot exceed 255 characters")
    private String street;

    @Size(max=255, message = "Cannot exceed 255 characters")
    private String section;

    @Size(max=255, message = "Cannot exceed 255 characters")
    private String number;

    @Size(max=255, message = "Cannot exceed 255 characters")
    private String floor;

    @Size(max=255, message = "Cannot exceed 255 characters")
    private String apartment;

    @Size(max=255, message = "Cannot exceed 255 characters")
    private String zip;

    private String complete_address;

    @NotNull(message = "PersonId cannot be null")
    private Long personId;

    private Long locationId;

    private Long provinceId;
}
