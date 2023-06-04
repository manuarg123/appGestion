package com.example.api.gender;

import com.example.api.paymentType.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findGenderByName(String name);
    List<Gender> findByDeletedAtIsNull();
    Optional<Gender> findByIdAndDeletedAtIsNull(Long id);
}
