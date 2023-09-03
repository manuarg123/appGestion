package com.example.api.plan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByDeletedAtIsNull();

    Optional<Plan> findByIdAndDeletedAtIsNull(Long id);

    Optional<Plan> findById(Long id);

    Page<Plan> findPageByDeletedAtIsNull(Pageable pageable);
}
