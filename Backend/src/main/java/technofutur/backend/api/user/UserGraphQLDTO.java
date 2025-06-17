package technofutur.backend.api.user;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import technofutur.backend.dal.entities.security.UserAuth;
import technofutur.backend.dal.entities.user.UserProfile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGraphQLDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public static UserGraphQLDTO fromEntities(UserAuth userAuth, UserProfile userProfile) {
        if (userAuth == null) {
            return null;
        }

        UserGraphQLDTO dto = new UserGraphQLDTO();
        dto.setId(userAuth.getId());
        dto.setUsername(userAuth.getUsername());
        dto.setRole(userAuth.getRole().toString());

        if (userProfile != null) {
            dto.setFirstName(userProfile.getFirstName());
            dto.setLastName(userProfile.getLastName());
            dto.setEmail(userProfile.getEmail());
        }

        return dto;
    }
}

