package com.example.api.paymentType;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentTypeService {
    HashMap<String , Object> data;
    private final PaymentTypeRepository paymentTypeRepository;
    @Autowired
    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository){this.paymentTypeRepository = paymentTypeRepository;}

    public List<PaymentType> getPaymentTypes() {
        return this.paymentTypeRepository.findByDeletedAtIsNull();
    }

    public APIResponse newPaymentType(PaymentTypeDTO paymentTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<PaymentType> res = paymentTypeRepository.findPaymentTypeByName(paymentTypeDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }
        PaymentType paymentType = new PaymentType();
        paymentType.setName(paymentTypeDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        paymentTypeRepository.save(paymentType);
        data.put("data", paymentType);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editPaymentType(Long id, PaymentTypeDTO paymentTypeDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<PaymentType> optionalPaymentType = paymentTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPaymentType.isPresent()) {
            PaymentType existingPaymentType = optionalPaymentType.get();
            existingPaymentType.setName(paymentTypeDTO.getName());

            paymentTypeRepository.save(existingPaymentType);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingPaymentType);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deletePaymentType(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.paymentTypeRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<PaymentType> optionalPaymentType = paymentTypeRepository.findById(id);
        PaymentType existingPaymentType = optionalPaymentType.get();
        existingPaymentType.setDeletedAt(LocalDateTime.now());

        paymentTypeRepository.save(existingPaymentType);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
