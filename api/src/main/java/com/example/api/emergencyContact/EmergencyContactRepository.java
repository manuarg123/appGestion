package com.example.api.emergencyContact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

    Optional<EmergencyContact> findByIdAndDeletedAtIsNull(Long id);
}
