package com.example.api.emergencyContact;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyContactDTO {
    private Long id;

    @NotNull(message = "Emergency Contact Name cannot be null")
    @Size(max = 144, message = "Name cannot be exceed 144 characters")
    private String name;

    @NotNull(message = "phoneNumber cannot be null")
    @Size(max = 144, message = "Name cannot be exceed 144 characters")
    private String phoneNumber;

    @NotNull(message = "personId cannot be null")
    private Long personId;

    public EmergencyContactDTO(Long id, @NotNull(message = "Emergency Contact Name cannot be null") String name, @NotNull(message = "phoneNumber cannot be null") String phoneNumber, @NotNull(message = "personId cannot be null") Long personId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.personId = personId;
    }

    public EmergencyContactDTO() {
    }
}
