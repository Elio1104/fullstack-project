package technofutur.backend.bll.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import technofutur.backend.dal.entities.security.User;

public interface UserService extends UserDetailsService {

    User login(String username, String password);

    User register(String email, String password, String role);

}
