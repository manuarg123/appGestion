package com.example.api.location;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    @Size(max= 144, message = "Value cannot be exceed 144 characters")
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "provinceId cannot be null")
    private Long provinceId;
}
