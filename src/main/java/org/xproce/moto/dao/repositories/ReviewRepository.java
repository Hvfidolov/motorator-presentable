package org.xproce.moto.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.entities.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMotorcycle(Motorcycle motorcycle);
}
