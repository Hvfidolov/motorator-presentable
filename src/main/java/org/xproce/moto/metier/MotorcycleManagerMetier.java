package org.xproce.moto.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.repositories.MotorcycleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleManagerMetier implements MotorcycleManager {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Override
    public Page<Motorcycle> searchMotorcycles(String keyword, int page, int taille) {
        return motorcycleRepository.findByNameContains(keyword, PageRequest.of(page, taille));
    }

    @Override
    public Motorcycle findById(Long id) {
        Optional<Motorcycle> motorcycle = motorcycleRepository.findById(id);
        return motorcycle.orElse(null);
    }

    @Override
    public void save(Motorcycle motorcycle) {
        motorcycleRepository.save(motorcycle);
    }

    @Override
    public void delete(Motorcycle motorcycle) {
        motorcycleRepository.delete(motorcycle);
    }

    @Override
    public List<Motorcycle> findAll() {
        return motorcycleRepository.findAll();
    }

    public List<Motorcycle> findByIds(List<Long> ids) {
        return motorcycleRepository.findAllById(ids);
    }
}
