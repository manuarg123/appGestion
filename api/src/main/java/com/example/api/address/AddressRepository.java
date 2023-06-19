package com.example.api.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByDeletedAtIsNull();
    Optional<Address> findByIdAndDeletedAtIsNull(Long id);
}
