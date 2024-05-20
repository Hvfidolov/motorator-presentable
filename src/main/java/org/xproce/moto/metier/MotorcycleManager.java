package org.xproce.moto.metier;

import org.springframework.data.domain.Page;
import org.xproce.moto.dao.entities.Motorcycle;

import java.util.List;

public interface MotorcycleManager {
    Page<Motorcycle> searchMotorcycles(String keyword, int page, int taille);
    Motorcycle findById(Long id);
    void save(Motorcycle motorcycle);
    void delete(Motorcycle motorcycle);
    List<Motorcycle> findAll();
    List<Motorcycle> findByIds(List<Long> ids);
}
