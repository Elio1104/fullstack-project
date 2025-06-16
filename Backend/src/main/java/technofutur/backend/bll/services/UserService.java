package technofutur.backend.bll.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import technofutur.backend.api.security.RegisterForm;
import technofutur.backend.dal.entities.security.User;

public interface UserService extends UserDetailsService {

    User login(String username, String password);

    User register(RegisterForm form, String role);

}
