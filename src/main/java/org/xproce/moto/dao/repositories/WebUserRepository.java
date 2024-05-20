package org.xproce.moto.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xproce.moto.dao.entities.WebUser;

import java.util.Optional;


public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    public Optional <WebUser> findByUsername(String username);
}