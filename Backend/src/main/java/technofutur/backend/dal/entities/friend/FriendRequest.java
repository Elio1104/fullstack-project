package technofutur.backend.dal.entities.friend;

import jakarta.persistence.*;
import lombok.*;
import technofutur.backend.dal.entities.BaseEntity;
import technofutur.backend.dal.entities.security.UserAuth;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "FRIEND_REQUEST")
@EqualsAndHashCode(callSuper = false)
@Entity
public class FriendRequest extends BaseEntity<Long> {

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private UserAuth sender;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private UserAuth receiver;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "STATUS")
    private FriendRequestStatus status;


}
