package com.example.api.speciality;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findSpecialityByName(String name);
    List<Speciality> findByDeletedAtIsNull();
    Optional<Speciality> findByIdAndDeletedAtIsNull(Long id);
    @Query("SELECT sp FROM Speciality sp WHERE sp.deletedAt IS NULL ORDER BY sp.name ASC")
    Page<Speciality> findPageByDeletedAtIsNull(Pageable pageable);
}
