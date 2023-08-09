package com.example.api.paymentType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    Optional<PaymentType> findPaymentTypeByName(String name);

    List<PaymentType> findByDeletedAtIsNull();

    Optional<PaymentType> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT py FROM PaymentType py WHERE py.deletedAt IS NULL ORDER BY py.name ASC")
    Page<PaymentType> findPageByDeletedAtIsNull(Pageable pageable);
}
