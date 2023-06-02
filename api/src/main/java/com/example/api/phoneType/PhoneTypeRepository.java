package com.example.api.phoneType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneTypeRepository extends JpaRepository<PhoneType, Long> {
    Optional<PhoneType> findPhoneTypeByName(String name);
    List<PhoneType> findByDeletedAtIsNull();
    Optional<PhoneType> findByIdAndDeletedAtIsNull(Long id);
}
