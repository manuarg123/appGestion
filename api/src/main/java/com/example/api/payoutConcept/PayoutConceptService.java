package com.example.api.payoutConcept;

import com.example.api.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
public class PayoutConceptService {
    private final PayoutConceptRepository payoutConceptRepository;

    @Autowired
    public PayoutConceptService(PayoutConceptRepository payoutConceptRepository) {
        this.payoutConceptRepository = payoutConceptRepository;
    }

    public List<PayoutConcept> getPayoutConcepts() {
        return this.payoutConceptRepository.findByDeletedAtIsNull();
    }


    public APIResponse newPayoutConcept(PayoutConceptDTO payoutConceptDTO) {
        if (Stream.of(payoutConceptDTO)
                .map(PayoutConceptDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<PayoutConcept> res = payoutConceptRepository.findPayoutConceptByName(payoutConceptDTO.getName());

        if (res.isPresent()) {
            PayoutConcept existingPayoutConcept = res.get();
            if (existingPayoutConcept.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        PayoutConcept payoutConcept = new PayoutConcept();
        payoutConcept.setName(payoutConceptDTO.getName());
        payoutConceptRepository.save(payoutConcept);

        apiResponse.setData(payoutConcept);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editPayoutConcept(Long id, PayoutConceptDTO payoutConceptDTO) {
        if (Stream.of(payoutConceptDTO)
                .map(PayoutConceptDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<PayoutConcept> optionalPayoutConcept = payoutConceptRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPayoutConcept.isPresent()) {
            Optional<PayoutConcept> res = payoutConceptRepository.findPayoutConceptByName(payoutConceptDTO.getName());

            if (res.isPresent()) {
                PayoutConcept existingPayoutConcept = res.get();
                if (existingPayoutConcept.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            PayoutConcept existingPayoutConcept = optionalPayoutConcept.get();
            existingPayoutConcept.setName(payoutConceptDTO.getName());
            payoutConceptRepository.save(existingPayoutConcept);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingPayoutConcept);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deletePayoutConcept(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<PayoutConcept> optionalPayoutConcept = payoutConceptRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPayoutConcept.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        PayoutConcept existingPayoutConcept = optionalPayoutConcept.get();
        existingPayoutConcept.setDeletedAt(LocalDateTime.now());
        payoutConceptRepository.save(existingPayoutConcept);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingPayoutConcept);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getPayoutConcept(Long id) {
        Optional<PayoutConcept> optionalPayoutConcept = payoutConceptRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPayoutConcept.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        PayoutConcept existingPayoutConcept = optionalPayoutConcept.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingPayoutConcept);
        return apiResponse;
    }

    public Page<PayoutConcept> getPayoutConceptPaginated(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return payoutConceptRepository.findPageByDeletedAtIsNull(pageable);
    }
}
