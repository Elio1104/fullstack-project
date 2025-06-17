package technofutur.backend.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import technofutur.backend.api.friend.FriendRequestDTO;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.bll.services.friend.FriendshipService;
import technofutur.backend.dal.entities.friend.FriendRequest;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FriendshipGraphQLController {
    private final FriendshipService friendshipService;

    private UserAuth getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAuth) {
            return (UserAuth) authentication.getPrincipal();
        }
        throw new IllegalStateException("User not authenticated");
    }

    @QueryMapping
    public List<UserGraphQLDTO> friends(@Argument Long userId) {
        List<UserAuth> friends = friendshipService.getFriends(userId);
        return friends.stream()
                .map(friend -> UserGraphQLDTO.fromEntities(friend, null))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<FriendRequestDTO> receivedFriendRequests() {
        UserAuth authenticatedUser = getAuthenticatedUser();
        List<FriendRequest> requests = friendshipService.getReceivedFriendRequests(authenticatedUser.getId());
        return requests.stream()
                .map(FriendRequestDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<FriendRequestDTO> sentFriendRequests() {
        UserAuth authenticatedUser = getAuthenticatedUser();
        List<FriendRequest> requests = friendshipService.getSentFriendRequests(authenticatedUser.getId());
        return requests.stream()
                .map(FriendRequestDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @MutationMapping
    public FriendRequestDTO sendFriendRequest(@Argument Long receiverId) {
        UserAuth authenticatedUser = getAuthenticatedUser();
        FriendRequest request = friendshipService.sendFriendRequest(authenticatedUser.getId(), receiverId);
        return FriendRequestDTO.fromEntity(request);
    }

    @MutationMapping
    public Boolean acceptFriendRequest(@Argument Long requestId) {
        friendshipService.acceptFriendRequest(requestId, getAuthenticatedUser().getId());
        return true;
    }

    @MutationMapping
    public Boolean rejectFriendRequest(@Argument Long requestId) {
        UserAuth authenticatedUser = getAuthenticatedUser();
        friendshipService.rejectFriendRequest(requestId, authenticatedUser.getId());
        return true;
    }

    @MutationMapping
    public Boolean cancelFriendRequest(@Argument Long requestId) {
        friendshipService.cancelFriendRequest(requestId, getAuthenticatedUser().getId());
        return true;
    }

    @MutationMapping
    public Boolean removeFriend(@Argument Long friendId) {
        friendshipService.removeFriend(getAuthenticatedUser().getId(), friendId);
        return true;
    }
}
