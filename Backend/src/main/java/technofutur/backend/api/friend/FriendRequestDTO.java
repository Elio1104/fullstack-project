package technofutur.backend.api.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.dal.entities.friend.FriendRequest;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDTO {
    private Long id;
    private UserGraphQLDTO sender;
    private UserGraphQLDTO receiver;
    private String status;
    private String createdAt;
    private String updatedAt;

    public static FriendRequestDTO fromEntity(FriendRequest friendRequest) {
        if (friendRequest == null) {
            return null;
        }

        FriendRequestDTO dto = new FriendRequestDTO();
        dto.setId(friendRequest.getId());
        dto.setSender(UserGraphQLDTO.fromEntities(friendRequest.getSender(), null));
        dto.setReceiver(UserGraphQLDTO.fromEntities(friendRequest.getReceiver(), null));
        dto.setStatus(friendRequest.getStatus().toString());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        dto.setCreatedAt(friendRequest.getCreatedAt().format(formatter));

        if (friendRequest.getUpdatedAt() != null) {
            dto.setUpdatedAt(friendRequest.getUpdatedAt().format(formatter));
        }

        return dto;
    }
}
