package com.example.api.speciality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findSpecialityByName(String name);
    List<Speciality> findByDeletedAtIsNull();
    Optional<Speciality> findByIdAndDeletedAtIsNull(Long id);
}
