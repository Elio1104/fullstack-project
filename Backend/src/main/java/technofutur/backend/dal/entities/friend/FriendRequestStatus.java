package technofutur.backend.dal.entities.friend;

public enum FriendRequestStatus {
    PENDING,  // The friend request has been sent but not yet accepted or rejected
    ACCEPTED, // The friend request has been accepted
    REJECTED, // The friend request has been rejected
    CANCELED  // The friend request has been canceled by the sender
}
