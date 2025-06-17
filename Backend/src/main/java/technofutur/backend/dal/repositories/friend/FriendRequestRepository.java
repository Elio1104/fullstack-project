package technofutur.backend.dal.repositories.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technofutur.backend.dal.entities.friend.FriendRequest;
import technofutur.backend.dal.entities.friend.FriendRequestStatus;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findBySenderAndStatus(UserAuth sender, FriendRequestStatus status);
    List<FriendRequest> findByReceiverAndStatus(UserAuth receiver, FriendRequestStatus status);
    Optional<FriendRequest> findBySenderAndReceiver(UserAuth sender, UserAuth receiver);
}
