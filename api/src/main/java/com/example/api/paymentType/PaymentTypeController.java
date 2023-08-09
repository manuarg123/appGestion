package com.example.api.paymentType;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "api/paymentTypes")
public class PaymentTypeController {

    @Autowired
    private final PaymentTypeService paymentTypeService;

    @Autowired
    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public List<PaymentType> getPaymentTypes() {
        return paymentTypeService.getPaymentTypes();
    }

    @GetMapping(path = "/show/{paymentTypeId}")
    public APIResponse getPaymentType(@PathVariable("paymentTypeId") Long id) {
        return this.paymentTypeService.getPaymentType(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addPaymentType(@RequestBody @Valid PaymentTypeDTO paymentTypeDTO) {
        return this.paymentTypeService.newPaymentType(paymentTypeDTO);
    }

    @PutMapping(path = "/edit/{paymentTypeId}")
    public APIResponse editPaymentType(@PathVariable("paymentTypeId") Long id, @Valid @RequestBody PaymentTypeDTO paymentTypeDTO) {
        return this.paymentTypeService.editPaymentType(id, paymentTypeDTO);
    }

    @DeleteMapping(path = "/delete/{paymentTypeId}")
    public APIResponse deletePaymentType(@PathVariable("paymentTypeId") Long id) {
        return this.paymentTypeService.deletePaymentType(id);
    }

    @GetMapping(path = "/paginated")
    public Page<PaymentType> getPaymentTypePaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return paymentTypeService.getPaymentTypePaginated(currentPage, pageSize);
    }
}
