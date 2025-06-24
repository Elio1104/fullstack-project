package technofutur.backend.dal.repositories.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technofutur.backend.dal.entities.message.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
