package com.example.api.payoutStatus;

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
@RequestMapping(path="api/payoutStatuses")
public class PayoutStatusController {

    @Autowired
    private final PayoutStatusService payoutStatusService;

    @Autowired
    public PayoutStatusController(PayoutStatusService payoutStatusService){this.payoutStatusService = payoutStatusService;}

    @GetMapping
    public List<PayoutStatus> getpayoutStatuses(){
        return payoutStatusService.getPayoutStatuses();
    }

    @GetMapping(path= "/show/{payoutStatusId}")
    public APIResponse getPayoutStatus(@PathVariable("payoutStatusId") Long id){
        return this.payoutStatusService.getPayoutStatus(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addPayoutStatus(@RequestBody @Valid PayoutStatusDTO payoutStatusDTO){
        return this.payoutStatusService.newPayoutStatus(payoutStatusDTO);
    }

    @PutMapping(path = "/edit/{payoutStatusId}")
    public APIResponse editPayoutStatus(@PathVariable("payoutStatusId") Long id, @Valid @RequestBody PayoutStatusDTO payoutStatusDTO){
        return this.payoutStatusService.editPayoutStatus(id, payoutStatusDTO);
    }

    @DeleteMapping(path="/delete/{payoutStatusId}")
    public APIResponse deletePayoutStatus(@PathVariable("payoutStatusId") Long id){
        return this.payoutStatusService.deletePayoutStatus(id);
    }

    @GetMapping(path = "/paginated")
    public Page<PayoutStatus> getPayoutStatusPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return payoutStatusService.getPayoutStatusPaginated(currentPage, pageSize);
    }
}
