package com.example.api.payoutStatus;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/payoutStatus")
public class PayoutStatusController {
    @Autowired
    private final PayoutStatusService payoutStatusService;
    public PayoutStatusController(PayoutStatusService payoutStatusService){this.payoutStatusService = payoutStatusService;}

    @GetMapping
    public List<PayoutStatus> getPayoutStatus(){return this.payoutStatusService.getPayoutStatus();}

    @PostMapping(path="/new")
    public APIResponse addPayoutStatus(@RequestBody PayoutStatusDTO payoutStatusDTO){
        return this.payoutStatusService.newPayoutStatus(payoutStatusDTO);
    }

    @PutMapping(path="/edit/{payoutStatusId}")
    public APIResponse editPayoutStatus(@PathVariable("payoutStatusId") Long id, @RequestBody PayoutStatusDTO payoutStatusDTO){
        return this.payoutStatusService.editPayoutStatus(id, payoutStatusDTO);
    }

    @DeleteMapping(path="/delete/{payoutStatusId}")
    public APIResponse deletePayoutStatus(@PathVariable("payoutStatusId") Long id){
        return this.payoutStatusService.deletePayoutStatus(id);
    }
}
