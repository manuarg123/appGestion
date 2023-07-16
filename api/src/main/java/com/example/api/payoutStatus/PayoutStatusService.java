package com.example.api.payoutStatus;

import com.example.api.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Validated
public class PayoutStatusService {
    HashMap<String, Object> data;
    private final PayoutStatusRepository payoutStatusRepository;

    @Autowired
    public PayoutStatusService(PayoutStatusRepository payoutStatusRepository) {
        this.payoutStatusRepository = payoutStatusRepository;
    }

    public List<PayoutStatus> getPayoutStatuses() {
        return this.payoutStatusRepository.findByDeletedAtIsNull();
    }


    public APIResponse newPayoutStatus(PayoutStatusDTO payoutStatusDTO) {
        if (Stream.of(payoutStatusDTO)
                .map(PayoutStatusDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<PayoutStatus> res = payoutStatusRepository.findPayoutStatusByName(payoutStatusDTO.getName());

        if (res.isPresent()) {
            PayoutStatus existingPayoutStatus = res.get();
            if (existingPayoutStatus.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        PayoutStatus payoutStatus = new PayoutStatus();
        payoutStatus.setName(payoutStatusDTO.getName());
        payoutStatusRepository.save(payoutStatus);

        apiResponse.setData(payoutStatus);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editPayoutStatus(Long id, PayoutStatusDTO payoutStatusDTO) {
        if (Stream.of(payoutStatusDTO)
                .map(PayoutStatusDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<PayoutStatus> optionalPayoutStatus = payoutStatusRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPayoutStatus.isPresent()) {
            Optional<PayoutStatus> res = payoutStatusRepository.findPayoutStatusByName(payoutStatusDTO.getName());

            if (res.isPresent()) {
                PayoutStatus existingPayoutStatus = res.get();
                if (existingPayoutStatus.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            PayoutStatus existingPayoutStatus = optionalPayoutStatus.get();
            existingPayoutStatus.setName(payoutStatusDTO.getName());
            payoutStatusRepository.save(existingPayoutStatus);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingPayoutStatus);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deletePayoutStatus(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<PayoutStatus> optionalPayoutStatus = payoutStatusRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPayoutStatus.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        PayoutStatus existingPayoutStatus = optionalPayoutStatus.get();
        existingPayoutStatus.setDeletedAt(LocalDateTime.now());
        payoutStatusRepository.save(existingPayoutStatus);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingPayoutStatus);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getPayoutStatus(Long id) {
        Optional<PayoutStatus> optionalPayoutStatus = payoutStatusRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPayoutStatus.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        PayoutStatus existingPayoutStatus = optionalPayoutStatus.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingPayoutStatus);
        return apiResponse;
    }
}
