package technofutur.backend.bll.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import technofutur.backend.api.security.RegisterForm;
import technofutur.backend.dal.entities.UserProfile;
import technofutur.backend.dal.entities.security.User;
import technofutur.backend.dal.entities.security.UserRole;
import technofutur.backend.dal.repositories.UserProfileRepository;
import technofutur.backend.dal.repositories.UserRepository;

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
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return user;
    }

    @Override
    public User register(RegisterForm form, String role) {
        if (userRepository.findByUsername(form.username()).isPresent()) {
            throw new IllegalArgumentException("User already exists with username: " + form.username() );
        }

        User user = new User();
        user.setUsername(form.username());
        user.setPassword(passwordEncoder.encode(form.password()));
        user.setRole(UserRole.valueOf(role));
        user = userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserAuth(user);
        userProfile.setFirstName(form.firstName());
        userProfile.setLastName(form.lastName());
        userProfile.setEmail(form.email());
        userProfileRepository.save(userProfile);


        return user;
    }
}
