package org.xproce.moto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xproce.moto.dao.entities.Motorcycle;
import org.xproce.moto.dao.entities.Review;
import org.xproce.moto.dao.entities.WebUser;
import org.xproce.moto.dao.repositories.WebUserRepository;
import org.xproce.moto.metier.WebUserManagerMetier;

@SpringBootApplication
public class MotoApplication implements CommandLineRunner {

    @Autowired
    WebUserRepository webUserRepository;

    public static void main(String[] args) {
        SpringApplication.run(MotoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
