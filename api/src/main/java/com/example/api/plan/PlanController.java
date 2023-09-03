package com.example.api.plan;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public List<PlanListDTO> getPlans() {return this.planService.getPlans();}

    @GetMapping(path = "/paginated")
    public Page<PlanListDTO> getPlansPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return planService.getPlansPaginated(currentPage, pageSize);
    }

    @PostMapping(path = "/new")
    public APIResponse newPlan(@RequestBody @Valid PlanDTO planDTO) {
        return this.planService.newPlan(planDTO);
    }

    @PutMapping(path = "/edit/{planId}")
    public APIResponse editPlan(@PathVariable("planId") Long id, @RequestBody @Valid PlanDTO planDTO) {
        return this.planService.editPlan(id, planDTO);
    }

    @DeleteMapping(path = "/delete/{planId}")
    public APIResponse deletePlan(@PathVariable("planId") Long id) {
        return this.planService.deletePlan(id);
    }

    @GetMapping(path = "/show/{planId}")
    public APIResponse getPlan(@PathVariable("planId") Long id) {
        return this.planService.getPlan(id);
    }
    }
