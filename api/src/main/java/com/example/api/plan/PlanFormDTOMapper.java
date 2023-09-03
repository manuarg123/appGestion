package com.example.api.plan;

import com.example.api.socialWork.SocialWorkFormPatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PlanFormDTOMapper implements Function<Plan, PlanFormDTO> {
    @Override
    public PlanFormDTO apply(Plan plan) {
        SocialWorkFormPatientDTO socialWorkFormPatientDTO = new SocialWorkFormPatientDTO();
        socialWorkFormPatientDTO.setId(plan.getSocialWork().getId());
        socialWorkFormPatientDTO.setName(plan.getSocialWork().getName());

        return new PlanFormDTO(
                plan.getId(),
                plan.getName(),
                socialWorkFormPatientDTO
        );
    }
}
