package com.example.api.identificationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long> {
    Optional<IdentificationType> findIdentificationTypeByName(String name);
    List<IdentificationType> findByDeletedAtIsNull();
    Optional<IdentificationType> findByIdAndDeletedAtIsNull(Long id);
}
