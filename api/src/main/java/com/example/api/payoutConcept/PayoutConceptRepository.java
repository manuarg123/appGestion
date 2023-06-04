package com.example.api.payoutConcept;

import com.example.api.paymentType.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayoutConceptRepository extends JpaRepository<PayoutConcept, Long> {
    Optional<PayoutConcept> findPayoutConceptByName(String name);
    List<PayoutConcept> findByDeletedAtIsNull();
    Optional<PayoutConcept> findByIdAndDeletedAtIsNull(Long id);
}
