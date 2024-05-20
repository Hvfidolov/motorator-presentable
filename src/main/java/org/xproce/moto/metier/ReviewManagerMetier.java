package org.xproce.moto.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.entities.Review;
import org.xproce.moto.dao.entities.WebUser;
import org.xproce.moto.dao.repositories.ReviewRepository;
import org.xproce.moto.dao.repositories.WebUserRepository;
import java.security.Principal;

import java.util.List;

@Service
public class ReviewManagerMetier implements ReviewManager{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WebUserRepository webUserRepository;

    public List<Review> findByMotorcycle(Motorcycle motorcycle) {
        return reviewRepository.findByMotorcycle(motorcycle);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}