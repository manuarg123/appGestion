package com.example.api.province;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province,Long> {
    Optional<Province> findProvinceByName(String name);
    List<Province> findByDeletedAtIsNull();
    Optional<Province> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT p FROM Province p WHERE p.deletedAt IS NULL")
    Page<Province> findPageByDeletedAtIsNull(Pageable pageable);
}
