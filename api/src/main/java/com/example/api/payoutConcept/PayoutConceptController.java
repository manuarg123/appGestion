package com.example.api.payoutConcept;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/payoutConcepts")
public class PayoutConceptController {
    @Autowired
    private final PayoutConceptService payoutConceptService;
    public PayoutConceptController(PayoutConceptService payoutConceptService){this.payoutConceptService = payoutConceptService;}

    @GetMapping
    public List<PayoutConcept> getPayoutConcepts(){return this.payoutConceptService.getPayoutConcepts();}

    @PostMapping(path="/new")
    public APIResponse addPayoutConcept(@RequestBody PayoutConceptDTO payoutConceptDTO){
        return this.payoutConceptService.newPayoutConcept(payoutConceptDTO);
    }

    @PutMapping(path="/edit/{payoutConceptId}")
    public APIResponse editPayoutConcept(@PathVariable("payoutConceptId") Long id, @RequestBody PayoutConceptDTO payoutConceptDTO){
        return this.payoutConceptService.editPayoutConcept(id, payoutConceptDTO);
    }

    @DeleteMapping(path="/delete/{payoutConceptId}")
    public APIResponse deletePayoutConcept(@PathVariable("payoutConceptId") Long id){
        return this.payoutConceptService.deletePayoutConcept(id);
    }
}
