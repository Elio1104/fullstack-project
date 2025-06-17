package technofutur.backend.dal.entities.user;

import jakarta.persistence.*;
import lombok.*;
import technofutur.backend.dal.entities.BaseEntity;
import technofutur.backend.dal.entities.security.UserAuth;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "USER_DETAILS")
public class UserProfile extends BaseEntity<Long> {

    @OneToOne
    @Getter @Setter
    @JoinColumn(name = "user_auth_id")
    private UserAuth userAuth;

    @Getter @Setter
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Getter @Setter
    @Column(name = "LAST_NAME")
    private String lastName;

    @Getter @Setter
    @Column(name = "EMAIL", unique = true, nullable = false, length = 100)
    private String email;
}
