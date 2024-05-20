package org.xproce.moto.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter

public class MotorcycleDTO {
    @NotEmpty(message = "Name required*")
    private String name;

    @NotEmpty(message = "Brand required*")
    private String brand;

    @NotEmpty(message = "Category required*")
    private String category;

    @Min(0)
    private Double price;

    private String imageUrl;
}
