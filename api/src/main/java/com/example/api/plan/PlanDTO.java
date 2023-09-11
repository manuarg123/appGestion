package com.example.api.plan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PlanDTO {

    private Long id;

    @Size(max = 144, message = "Plan name cannot be exceed 144 characters")
    private String name;

    private Long socialWorkId;
}
