package com.example.api.payoutConcept;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "api/payoutConcepts")
public class PayoutConceptController {

    @Autowired
    private final PayoutConceptService payoutConceptService;

    @Autowired
    public PayoutConceptController(PayoutConceptService payoutConceptService) {
        this.payoutConceptService = payoutConceptService;
    }

    @GetMapping
    public List<PayoutConcept> getPayoutConcepts() {
        return payoutConceptService.getPayoutConcepts();
    }

    @GetMapping(path = "/show/{payoutConceptId}")
    public APIResponse getPayoutConcept(@PathVariable("payoutConceptId") Long id) {
        return this.payoutConceptService.getPayoutConcept(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addPayoutConcept(@RequestBody @Valid PayoutConceptDTO payoutConceptDTO) {
        return this.payoutConceptService.newPayoutConcept(payoutConceptDTO);
    }

    @PutMapping(path = "/edit/{payoutConceptId}")
    public APIResponse editPayoutConcept(@PathVariable("payoutConceptId") Long id, @Valid @RequestBody PayoutConceptDTO payoutConceptDTO) {
        return this.payoutConceptService.editPayoutConcept(id, payoutConceptDTO);
    }

    @DeleteMapping(path = "/delete/{payoutConceptId}")
    public APIResponse deletePayoutConcept(@PathVariable("payoutConceptId") Long id) {
        return this.payoutConceptService.deletePayoutConcept(id);
    }

    @GetMapping(path = "/paginated")
    public Page<PayoutConcept> getPayoutConceptPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return payoutConceptService.getPayoutConceptPaginated(currentPage, pageSize);
    }
}
