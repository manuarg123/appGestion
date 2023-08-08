package com.example.api.gender;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender,Long> {
    Optional<Gender> findGenderByName(String name);
    List<Gender> findByDeletedAtIsNull();
    Optional<Gender> findByIdAndDeletedAtIsNull(Long id);
    Optional<Gender> findGenderByNameAndDeletedAtIsNull(String name);

    @Query("SELECT g FROM Gender g WHERE g.deletedAt IS NULL ORDER BY g.name ASC")
    Page<Gender> findPageByDeletedAtIsNull(Pageable pageable);
}
