package com.example.api.emalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTypeRepository extends JpaRepository<EmailType, Long> {
    Optional<EmailType> findEmailTypeByName(String name);
    List<EmailType> findByDeletedAtIsNull();
    Optional<EmailType> findByIdAndDeletedAtIsNull(Long id);
    @Query("SELECT et FROM EmailType et WHERE et.deletedAt IS NULL ORDER BY et.name ASC")
    Page<EmailType> findPageByDeletedAtIsNull(Pageable pageable);
}
