package technofutur.backend.dal.entities.message;

import jakarta.persistence.*;
import lombok.*;
import technofutur.backend.dal.entities.BaseEntity;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONVERSATION")
@ToString
@EqualsAndHashCode(callSuper = false)
public class Conversation extends BaseEntity<Long> {

    @Getter @Setter
    @ManyToMany
    @JoinTable(
            name = "CONVERSATION_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_auth_id")
    )
    private Set<UserAuth> participants = new HashSet<>();

    @Getter @Setter
    @OneToMany(
            mappedBy = "conversation",
            cascade = CascadeType.ALL
    )
    private List<Message> messages = new ArrayList<>();
}
