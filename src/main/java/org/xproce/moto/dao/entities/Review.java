package org.xproce.moto.dao.entities;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Motorcycle motorcycle;

    @ManyToOne
    private WebUser webUser;

    private String content;

    private LocalDateTime date;
}
