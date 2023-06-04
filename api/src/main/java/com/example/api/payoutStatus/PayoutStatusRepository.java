package com.example.api.payoutStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayoutStatusRepository extends JpaRepository<PayoutStatus, Long> {
    Optional<PayoutStatus> findPayoutStatusByName(String name);
    List<PayoutStatus> findByDeletedAtIsNull();
    Optional<PayoutStatus> findByIdAndDeletedAtIsNull(Long id);
}
