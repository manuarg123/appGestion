package com.example.api.socialWork;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SocialWorkRepository extends JpaRepository<SocialWork, Long> {
    Optional<SocialWork> findSocialWorkByName(String name);
    List<SocialWork> findByDeletedAtIsNull();
    Optional<SocialWork> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = { "addresses", "phones", "emails", "identifications" })
    Optional<SocialWork> findById(Long id);

    @Query("SELECT m FROM SocialWork m WHERE m.deletedAt IS NULL")
    Page<SocialWork> findPageByDeletedAtIsNull(Pageable pageable);
}