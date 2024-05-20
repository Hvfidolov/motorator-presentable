package org.xproce.moto.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;
import org.xproce.moto.dao.entities.WebUser;
import org.xproce.moto.dao.repositories.WebUserRepository;

@Service
public class WebUserManagerMetier implements WebUserManager{

    @Autowired
    private WebUserRepository webUserRepository;

    public WebUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return webUserRepository.findByUsername(username).get();
    }

    @Override
    public WebUser findByUsername(String username) {
        return webUserRepository.findByUsername(username).orElse(null);
    }

}
