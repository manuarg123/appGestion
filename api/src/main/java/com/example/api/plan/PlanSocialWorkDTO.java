package com.example.api.plan;

import com.example.api.socialWork.SocialWorkPlanDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PlanSocialWorkDTO {
    private Long id;
    private String name;
    private SocialWorkPlanDTO socialWork;
    private String code;
}
