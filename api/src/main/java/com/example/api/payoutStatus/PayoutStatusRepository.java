package com.example.api.payoutStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayoutStatusRepository extends JpaRepository<PayoutStatus, Long> {
    Optional<PayoutStatus> findPayoutStatusByName(String name);
    List<PayoutStatus> findByDeletedAtIsNull();
    Optional<PayoutStatus> findByIdAndDeletedAtIsNull(Long id);
    @Query("SELECT ps FROM PayoutStatus ps WHERE ps.deletedAt IS NULL ORDER BY ps.name ASC")
    Page<PayoutStatus> findPageByDeletedAtIsNull(Pageable pageable);
}
