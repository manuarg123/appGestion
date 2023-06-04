package com.example.api.paymentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Optional<PaymentType> findPaymentTypeByName(String name);
    List<PaymentType> findByDeletedAtIsNull();
    Optional<PaymentType> findByIdAndDeletedAtIsNull(Long id);
}
