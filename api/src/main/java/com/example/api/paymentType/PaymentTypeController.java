package com.example.api.paymentType;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/paymentTypes")
public class PaymentTypeController {
    @Autowired
    private final PaymentTypeService paymentTypeService;
    public PaymentTypeController(PaymentTypeService paymentTypeService){this.paymentTypeService = paymentTypeService;}

    @GetMapping
    public List<PaymentType> getPaymentTypes(){return this.paymentTypeService.getPaymentTypes();}

    @PostMapping(path="/new")
    public APIResponse addPaymentType(@RequestBody PaymentTypeDTO paymentTypeDTO){
        return this.paymentTypeService.newPaymentType(paymentTypeDTO);
    }

    @PutMapping(path="/edit/{paymentTypeId}")
    public APIResponse editPaymentType(@PathVariable("paymentTypeId") Long id, @RequestBody PaymentTypeDTO paymentTypeDTO){
        return this.paymentTypeService.editPaymentType(id, paymentTypeDTO);
    }

    @DeleteMapping(path="/delete/{paymentTypeId}")
    public APIResponse deletePaymentType(@PathVariable("paymentTypeId") Long id){
        return this.paymentTypeService.deletePaymentType(id);
    }
}
