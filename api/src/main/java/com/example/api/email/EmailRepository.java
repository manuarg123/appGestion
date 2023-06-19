package com.example.api.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findByDeletedAtIsNull();
    Optional<Email> findByIdAndDeletedAtIsNull(Long id);
}