package com.example.api.plan;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.patient.PatientFormDTOMapper;
import com.example.api.socialWork.SocialWork;
import com.example.api.socialWork.SocialWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final SocialWorkRepository socialWorkRepository;
    private final PlanFormDTOMapper planFormDTOMapper;
    private final PlanListDTOMapper planListDTOMapper;

    public List<PlanListDTO> getPlans() {
        List<PlanListDTO> planListDTOS = planRepository.findByDeletedAtIsNull()
                .stream()
                .map(planListDTOMapper::apply)
                .collect(Collectors.toList());

        return planListDTOS;
    }

    public APIResponse newPlan(PlanDTO planDTO) {
        APIResponse apiResponse = new APIResponse();
        Plan plan = new Plan();
        plan.setName(planDTO.getName());

        Optional<SocialWork> optionalSocialWork = findSocialWork(planDTO.getSocialWorkId());
        plan.setSocialWork(optionalSocialWork.get());

        planRepository.save(plan);

        apiResponse.setData(plan);
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setMessage(MessagesResponse.addSuccess);

        return apiResponse;
    }

    public APIResponse editPlan(Long id, PlanDTO planDTO) {
        APIResponse apiResponse = new APIResponse();
        Optional<Plan> optionalPlan = findPlan(id);

        Plan existingPlan = optionalPlan.get();
        existingPlan.setName(planDTO.getName());

        Optional<SocialWork> optionalSocialWork = findSocialWork(planDTO.getSocialWorkId());
        existingPlan.setSocialWork(optionalSocialWork.get());

        planRepository.save(existingPlan);

        apiResponse.setData(existingPlan);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.editSuccess);

        return apiResponse;
    }

    public APIResponse deletePlan(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Plan> optionalPlan = findPlan(id);

        Plan existingPlan = optionalPlan.get();
        existingPlan.setDeletedAt(LocalDateTime.now());
        planRepository.save(existingPlan);

        apiResponse.setData(existingPlan);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(MessagesResponse.deleteSuccess);

        return  apiResponse;
    }

    public Optional<SocialWork> findSocialWork(Long id) {
        Optional<SocialWork> optionalSocialWork = socialWorkRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalSocialWork.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalSocialWork;
    }

    public APIResponse getPlan(Long id) {
        Optional<Plan> optionalPlan = findPlan(id);
        APIResponse apiResponse = new APIResponse();

        PlanFormDTO planFormDTO = planFormDTOMapper.apply(optionalPlan.get());

        apiResponse.setData(planFormDTO);
        apiResponse.setStatus(HttpStatus.OK.value());

        return apiResponse;
    }

    public Optional<Plan> findPlan(Long id) {
        Optional<Plan> optionalPlan = planRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPlan.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        return optionalPlan;
    }

    public Page<PlanListDTO> getPlansPaginated(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Plan> planPage = planRepository.findPageByDeletedAtIsNull(pageable);
        Page<PlanListDTO> planListDTOS = planPage.map(planListDTOMapper::apply);

        return planListDTOS;
    }
}
