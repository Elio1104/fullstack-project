package technofutur.backend.dal.repositories.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technofutur.backend.dal.entities.friend.Friendship;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser(UserAuth user);
    Optional<Friendship> findByUserAndFriend(UserAuth user, UserAuth friend);
}
