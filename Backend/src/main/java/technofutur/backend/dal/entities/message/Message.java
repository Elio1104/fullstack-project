package technofutur.backend.dal.entities.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import technofutur.backend.dal.entities.BaseEntity;
import technofutur.backend.dal.entities.security.UserAuth;

@Entity
public class Message extends BaseEntity<Long> {
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "CONVERSATION_ID", nullable = false)
    private Conversation conversation;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "SENDER_ID", nullable = false)
    private UserAuth sender;

    @Getter @Setter
    @Column(name = "CONTENT", nullable = false)
    private String content;
}
