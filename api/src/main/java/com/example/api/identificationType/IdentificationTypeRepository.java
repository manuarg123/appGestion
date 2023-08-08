package com.example.api.identificationType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long> {
    Optional<IdentificationType> findIdentificationTypeByName(String name);
    List<IdentificationType> findByDeletedAtIsNull();
    Optional<IdentificationType> findByIdAndDeletedAtIsNull(Long id);
    @Query("SELECT it FROM IdentificationType it WHERE it.deletedAt IS NULL ORDER BY it.name ASC")
    Page<IdentificationType> findPageByDeletedAtIsNull(Pageable pageable);
}
