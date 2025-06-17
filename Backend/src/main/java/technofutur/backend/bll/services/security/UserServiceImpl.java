package technofutur.backend.bll.services.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import technofutur.backend.api.security.RegisterForm;
import technofutur.backend.dal.entities.user.UserProfile;
import technofutur.backend.dal.entities.security.UserAuth;
import technofutur.backend.dal.entities.security.UserRole;
import technofutur.backend.dal.repositories.user.UserProfileRepository;
import technofutur.backend.dal.repositories.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserAuth login(String username, String password) {
        UserAuth userAuth = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (!passwordEncoder.matches(password, userAuth.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return userAuth;
    }

    @Override
    public UserAuth register(RegisterForm form, String role) {
        if (userRepository.findByUsername(form.username()).isPresent()) {
            throw new IllegalArgumentException("User already exists with username: " + form.username() );
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(form.username());
        userAuth.setPassword(passwordEncoder.encode(form.password()));
        userAuth.setRole(UserRole.valueOf(role));
        userAuth = userRepository.save(userAuth);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserAuth(userAuth);
        userProfile.setFirstName(form.firstName());
        userProfile.setLastName(form.lastName());
        userProfile.setEmail(form.email());
        userProfileRepository.save(userProfile);

        return userAuth;
    }
}
