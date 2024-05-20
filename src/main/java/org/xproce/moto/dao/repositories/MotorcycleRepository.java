package org.xproce.moto.dao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.moto.dao.entities.Motorcycle;

import java.util.Optional;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    Page<Motorcycle> findByNameContains(String keyword, Pageable pageable);
    Optional<Motorcycle> findById(Long id);  // Correct the method signature

}
