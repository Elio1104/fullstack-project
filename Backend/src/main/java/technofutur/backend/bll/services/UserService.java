package technofutur.backend.bll.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import technofutur.backend.api.security.RegisterForm;
import technofutur.backend.dal.entities.security.UserAuth;

public interface UserService extends UserDetailsService {

    UserAuth login(String username, String password);

    UserAuth register(RegisterForm form, String role);

}
