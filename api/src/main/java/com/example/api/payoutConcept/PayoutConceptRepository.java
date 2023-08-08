package com.example.api.payoutConcept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayoutConceptRepository extends JpaRepository<PayoutConcept, Long> {
    Optional<PayoutConcept> findPayoutConceptByName(String name);

    List<PayoutConcept> findByDeletedAtIsNull();

    Optional<PayoutConcept> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT pc FROM PayoutConcept pc WHERE pc.deletedAt IS NULL ORDER BY pc.name ASC")
    Page<PayoutConcept> findPageByDeletedAtIsNull(Pageable pageable);
}
