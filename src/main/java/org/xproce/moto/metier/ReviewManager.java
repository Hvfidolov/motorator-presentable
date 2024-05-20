package org.xproce.moto.metier;

import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.entities.Review;

import java.util.List;

public interface ReviewManager {
    public List<Review> findByMotorcycle(Motorcycle motorcycle);
    public void saveReview(Review review);
}
