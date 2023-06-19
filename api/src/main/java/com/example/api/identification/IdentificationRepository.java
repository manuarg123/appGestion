package com.example.api.identification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdentificationRepository extends JpaRepository<Identification, Long> {
    List<Identification> findByDeletedAtIsNull();
    Optional<Identification> findByIdAndDeletedAtIsNull(Long id);
}