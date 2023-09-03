package com.example.api.plan;

import com.example.api.socialWork.SocialWorkFormPatientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PlanFormDTO {
    private Long id;
    private String name;
    private SocialWorkFormPatientDTO socialWork;
}
