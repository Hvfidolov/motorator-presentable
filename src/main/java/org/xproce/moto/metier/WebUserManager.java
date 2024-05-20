package org.xproce.moto.metier;

import org.xproce.moto.dao.entities.WebUser;

public interface WebUserManager {
    public WebUser getCurrentUser();
    public WebUser findByUsername(String username);
}
