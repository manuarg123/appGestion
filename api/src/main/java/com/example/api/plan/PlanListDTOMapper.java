package com.example.api.plan;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlanListDTOMapper implements Function<Plan, PlanListDTO> {
    @Override
    public PlanListDTO apply(Plan plan){
        return new PlanListDTO(
                plan.getId(),
                plan.getName(),
                plan.getSocialWork().getName()
        );
    }
}
