package technofutur.backend.bll.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.dal.entities.security.UserAuth;
import technofutur.backend.dal.entities.user.UserProfile;
import technofutur.backend.dal.repositories.user.UserProfileRepository;
import technofutur.backend.dal.repositories.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGraphQLService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public List<UserGraphQLDTO> getAllUsers() {
        List<UserAuth> userAuths = userRepository.findAll();

        return userAuths.stream()
                .map(userAuth -> {
                    UserProfile userProfile = userProfileRepository.findByUserAuthId(userAuth.getId()).orElse(null);
                    return UserGraphQLDTO.fromEntities(userAuth, userProfile);
                })
                .collect(Collectors.toList());
    }

    public UserGraphQLDTO getUserById(Long id) {
        Optional<UserAuth> userAuth = userRepository.findById(id);
        if (userAuth.isEmpty()) {
            return null;
        }

        UserProfile userProfile = userProfileRepository.findByUserAuthId(id).orElse(null);
        return UserGraphQLDTO.fromEntities(userAuth.get(), userProfile);
    }

    public UserGraphQLDTO getCurrentUser(UserAuth userAuth) {
        if (userAuth == null) {
            return null;
        }

        UserProfile userProfile = userProfileRepository.findByUserAuthId(userAuth.getId()).orElse(null);
        return UserGraphQLDTO.fromEntities(userAuth, userProfile);
    }
}
