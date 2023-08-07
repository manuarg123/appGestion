package com.example.api.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findLocationByName(String name);
    List<Location> findByDeletedAtIsNull();
    Optional<Location> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT l FROM Location l WHERE l.deletedAt IS NULL ORDER BY l.name ASC")
    Page<Location> findPageByDeletedAtIsNull(Pageable pageable);
}
