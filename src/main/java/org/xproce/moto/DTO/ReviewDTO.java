package org.xproce.moto.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.entities.WebUser;

public class ReviewDTO {
    @NotEmpty(message = "motorcycle required*")
    private Motorcycle motorcycle;

    @NotEmpty(message = "user required*")
    private WebUser user;

    @NotEmpty(message = "text required*")
    private String text;
    @NotEmpty(message = "rating required*")
    private int rating;
}
