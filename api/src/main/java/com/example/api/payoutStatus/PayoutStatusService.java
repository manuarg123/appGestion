package com.example.api.payoutStatus;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PayoutStatusService {
    HashMap<String , Object> data;
    private final PayoutStatusRepository payoutStatusRepository;
    @Autowired
    public PayoutStatusService(PayoutStatusRepository payoutStatusRepository){this.payoutStatusRepository = payoutStatusRepository;}

    public List<PayoutStatus> getPayoutStatus() {
        return this.payoutStatusRepository.findByDeletedAtIsNull();
    }

    public APIResponse newPayoutStatus(PayoutStatusDTO payoutStatusDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<PayoutStatus> res = payoutStatusRepository.findPayoutStatusByName(payoutStatusDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }
        PayoutStatus payoutStatus = new PayoutStatus();
        payoutStatus.setName(payoutStatusDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        payoutStatusRepository.save(payoutStatus);
        data.put("data", payoutStatus);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editPayoutStatus(Long id, PayoutStatusDTO payoutStatusDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<PayoutStatus> optionalPayoutStatus = payoutStatusRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPayoutStatus.isPresent()) {
            PayoutStatus existingPayoutStatus = optionalPayoutStatus.get();
            existingPayoutStatus.setName(payoutStatusDTO.getName());

            payoutStatusRepository.save(existingPayoutStatus);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingPayoutStatus);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deletePayoutStatus(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.payoutStatusRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<PayoutStatus> optionalPayoutStatus = payoutStatusRepository.findById(id);
        PayoutStatus existingPayoutStatus = optionalPayoutStatus.get();
        existingPayoutStatus.setDeletedAt(LocalDateTime.now());

        payoutStatusRepository.save(existingPayoutStatus);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
