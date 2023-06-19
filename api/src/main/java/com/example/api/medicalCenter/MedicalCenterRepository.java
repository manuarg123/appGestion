package com.example.api.medicalCenter;

import com.example.api.location.Location;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalCenterRepository extends JpaRepository<MedicalCenter, Long> {
    Optional<MedicalCenter> findMedicalCenterByName(String name);
    List<MedicalCenter> findByDeletedAtIsNull();
    Optional<MedicalCenter> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = { "addresses", "phones", "emails", "identifications" })
    Optional<MedicalCenter> findById(Long id);
}
