package com.example.api.paymentType;

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
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    @Autowired
    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public List<PaymentType> getPaymentTypes() {
        return this.paymentTypeRepository.findByDeletedAtIsNull();
    }


    public APIResponse newPaymentType(PaymentTypeDTO paymentTypeDTO) {
        if (Stream.of(paymentTypeDTO)
                .map(PaymentTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<PaymentType> res = paymentTypeRepository.findPaymentTypeByName(paymentTypeDTO.getName());

        if (res.isPresent()) {
            PaymentType existingPaymentType = res.get();
            if (existingPaymentType.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        PaymentType paymentType = new PaymentType();
        paymentType.setName(paymentTypeDTO.getName());
        paymentTypeRepository.save(paymentType);

        apiResponse.setData(paymentType);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editPaymentType(Long id, PaymentTypeDTO paymentTypeDTO) {
        if (Stream.of(paymentTypeDTO)
                .map(PaymentTypeDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<PaymentType> optionalPaymentType = paymentTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalPaymentType.isPresent()) {
            Optional<PaymentType> res = paymentTypeRepository.findPaymentTypeByName(paymentTypeDTO.getName());

            if (res.isPresent()) {
                PaymentType existingPaymentType = res.get();
                if (existingPaymentType.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            PaymentType existingPaymentType = optionalPaymentType.get();
            existingPaymentType.setName(paymentTypeDTO.getName());
            paymentTypeRepository.save(existingPaymentType);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingPaymentType);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deletePaymentType(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<PaymentType> optionalPaymentType = paymentTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPaymentType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        PaymentType existingPaymentType = optionalPaymentType.get();
        existingPaymentType.setDeletedAt(LocalDateTime.now());
        paymentTypeRepository.save(existingPaymentType);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingPaymentType);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getPaymentType(Long id) {
        Optional<PaymentType> optionalPaymentType = paymentTypeRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalPaymentType.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        PaymentType existingPaymentType = optionalPaymentType.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingPaymentType);
        return apiResponse;
    }

    public Page<PaymentType> getPaymentTypePaginated(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return paymentTypeRepository.findPageByDeletedAtIsNull(pageable);
    }
}
