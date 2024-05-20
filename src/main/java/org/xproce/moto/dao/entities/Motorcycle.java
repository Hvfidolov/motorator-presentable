package org.xproce.moto.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "Motorcycles")
public class Motorcycle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String category;
    private double price;
    private String imageFileName;
    @OneToMany(mappedBy = "motorcycle")
    private Set<Review> reviews;

}
