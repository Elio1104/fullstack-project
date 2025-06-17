package technofutur.backend.api.security;

import lombok.Data;
import technofutur.backend.dal.entities.security.UserAuth;
import technofutur.backend.dal.entities.security.UserRole;

@Data
public class UserTokenDTO {

    private Long id;
    private String username;
    private UserRole role;
    private String token;

    public UserTokenDTO(Long id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static UserTokenDTO fromEntity(UserAuth userAuth) {
        return new UserTokenDTO(userAuth.getId(), userAuth.getUsername(), userAuth.getRole());
    }
}
