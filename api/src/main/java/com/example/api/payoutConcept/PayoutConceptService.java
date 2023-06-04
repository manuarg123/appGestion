package com.example.api.payoutConcept;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PayoutConceptService {
    HashMap<String , Object> data;
    private final PayoutConceptRepository payoutConceptRepository;
    @Autowired
    public PayoutConceptService(PayoutConceptRepository payoutConceptRepository){this.payoutConceptRepository = payoutConceptRepository;}

    public List<PayoutConcept> getPayoutConcepts() {
        return this.payoutConceptRepository.findByDeletedAtIsNull();
    }

    public APIResponse newPayoutConcept(PayoutConceptDTO payoutConceptDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<PayoutConcept> res = payoutConceptRepository.findPayoutConceptByName(payoutConceptDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }
        PayoutConcept payoutConcept = new PayoutConcept();
        payoutConcept.setName(payoutConceptDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        payoutConceptRepository.save(payoutConcept);
        data.put("data", payoutConcept);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editPayoutConcept(Long id, PayoutConceptDTO payoutConceptDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<PayoutConcept> optionalPayoutConcept = payoutConceptRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPayoutConcept.isPresent()) {
            PayoutConcept existingPayoutConcept = optionalPayoutConcept.get();
            existingPayoutConcept.setName(payoutConceptDTO.getName());

            payoutConceptRepository.save(existingPayoutConcept);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingPayoutConcept);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deletePayoutConcept(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.payoutConceptRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<PayoutConcept> optionalPayoutConcept = payoutConceptRepository.findById(id);
        PayoutConcept existingPayoutConcept = optionalPayoutConcept.get();
        existingPayoutConcept.setDeletedAt(LocalDateTime.now());

        payoutConceptRepository.save(existingPayoutConcept);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
