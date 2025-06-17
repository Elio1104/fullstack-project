package technofutur.backend.bll.services.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technofutur.backend.dal.entities.friend.FriendRequest;
import technofutur.backend.dal.entities.friend.FriendRequestStatus;
import technofutur.backend.dal.entities.friend.Friendship;
import technofutur.backend.dal.entities.security.UserAuth;
import technofutur.backend.dal.repositories.user.UserRepository;
import technofutur.backend.dal.repositories.friend.FriendRequestRepository;
import technofutur.backend.dal.repositories.friend.FriendshipRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    // Envoyer une demande d'ami
    @Transactional
    public FriendRequest sendFriendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Vous ne pouvez pas vous envoyer une demande d'ami à vous-même");
        }

        UserAuth sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur expéditeur non trouvé"));
        UserAuth receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur destinataire non trouvé"));

        // Vérifier si une demande existe déjà
        Optional<FriendRequest> existingRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (existingRequest.isPresent()) {
            throw new IllegalArgumentException("Une demande d'ami existe déjà");
        }

        // Vérifier si une amitié existe déjà
        Optional<Friendship> existingFriendship = friendshipRepository.findByUserAndFriend(sender, receiver);
        if (existingFriendship.isPresent()) {
            throw new IllegalArgumentException("Vous êtes déjà amis avec cet utilisateur");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        friendRequest.setCreatedAt(LocalDateTime.now());

        return friendRequestRepository.save(friendRequest);
    }

    // Accepter une demande d'ami
    @Transactional
    public void acceptFriendRequest(Long requestId, Long userId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Demande d'ami non trouvée"));

        if (!request.getReceiver().getId().equals(userId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à accepter cette demande");
        }

        if (request.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("Cette demande a déjà été traitée");
        }

        // Mettre à jour la demande
        request.setStatus(FriendRequestStatus.ACCEPTED);
        request.setUpdatedAt(LocalDateTime.now());
        friendRequestRepository.save(request);

        // Créer une relation d'amitié bidirectionnelle
        Friendship friendship1 = new Friendship();
        friendship1.setUser(request.getSender());
        friendship1.setFriend(request.getReceiver());
        friendshipRepository.save(friendship1);

        Friendship friendship2 = new Friendship();
        friendship2.setUser(request.getReceiver());
        friendship2.setFriend(request.getSender());
        friendshipRepository.save(friendship2);
    }

    // Rejeter une demande d'ami
    @Transactional
    public void rejectFriendRequest(Long requestId, Long userId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Demande d'ami non trouvée"));

        if (!request.getReceiver().getId().equals(userId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à rejeter cette demande");
        }

        if (request.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("Cette demande a déjà été traitée");
        }

        request.setStatus(FriendRequestStatus.REJECTED);
        request.setUpdatedAt(LocalDateTime.now());
        friendRequestRepository.save(request);
    }

    // Annuler une demande d'ami
    @Transactional
    public void cancelFriendRequest(Long requestId, Long userId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Demande d'ami non trouvée"));

        if (!request.getSender().getId().equals(userId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à annuler cette demande");
        }

        if (request.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("Cette demande a déjà été traitée");
        }

        friendRequestRepository.delete(request);
    }

    // Supprimer un ami
    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        UserAuth user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        UserAuth friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Ami non trouvé"));

        Friendship friendship1 = friendshipRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("Relation d'amitié non trouvée"));
        Friendship friendship2 = friendshipRepository.findByUserAndFriend(friend, user)
                .orElseThrow(() -> new IllegalArgumentException("Relation d'amitié non trouvée"));

        friendshipRepository.delete(friendship1);
        friendshipRepository.delete(friendship2);
    }

    // Obtenir les demandes d'ami reçues
    public List<FriendRequest> getReceivedFriendRequests(Long userId) {
        UserAuth user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        return friendRequestRepository.findByReceiverAndStatus(user, FriendRequestStatus.PENDING);
    }

    // Obtenir les demandes d'ami envoyées
    public List<FriendRequest> getSentFriendRequests(Long userId) {
        UserAuth user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        return friendRequestRepository.findBySenderAndStatus(user, FriendRequestStatus.PENDING);
    }

    // Obtenir la liste d'amis
    public List<UserAuth> getFriends(Long userId) {
        UserAuth user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        return friendshipRepository.findByUser(user).stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());
    }
}
