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
    @Column(name = "SENDER_ID")
    private UserAuth senderId;

    @Getter @Setter
    @ManyToOne
    @Column(name = "RECEIVER_ID")
    private UserAuth receiverId;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private FriendRequestStatus status;


}
