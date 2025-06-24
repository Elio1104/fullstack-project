package technofutur.backend.dal.repositories.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technofutur.backend.dal.entities.message.Conversation;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByParticipantsContaining(UserAuth user);
    Optional<Conversation> findByParticipantsContainingAndParticipantsContaining(UserAuth sender, UserAuth receiver);
}
