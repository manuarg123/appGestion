package com.example.api.professional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long>{
    List<Professional> findByDeletedAtIsNull();

    Optional<Professional> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = {"addresses", "phones", "emails", "identifications", "medicalCenters", "clinicHistories"})
    Optional<Professional> findById(Long id);

    @Query("SELECT p FROM Professional p WHERE p.deletedAt IS NULL")
    Page<Professional> findPageByDeletedAtIsNull(Pageable pageable);
}
