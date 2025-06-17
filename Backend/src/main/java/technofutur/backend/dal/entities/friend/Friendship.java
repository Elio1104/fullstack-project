package technofutur.backend.dal.entities.friend;

import jakarta.persistence.*;
import lombok.*;
import technofutur.backend.dal.entities.BaseEntity;
import technofutur.backend.dal.entities.security.UserAuth;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "FRIENDSHIP",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_id"}))
public class Friendship extends BaseEntity<Long> {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAuth user;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private UserAuth friend;

}