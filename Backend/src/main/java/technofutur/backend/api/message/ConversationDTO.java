package technofutur.backend.api.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.dal.entities.message.Conversation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private Long id;
    private Set<UserGraphQLDTO> participants = new HashSet<>();
    private List<MessageDTO> messages = new ArrayList<>();
    private String createdAt;
    private String updatedAt;

    public static ConversationDTO fromEntity(Conversation conversation) {
        if (conversation == null) {
            return null;
        }

        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getId());

        // Convert participants to UserGraphQLDTO
        if (conversation.getParticipants() != null) {
            dto.setParticipants(
                    conversation.getParticipants().stream()
                            .map(user -> UserGraphQLDTO.fromEntities(user, null))
                            .collect(Collectors.toSet())
            );
        }

        // Convert messages to MessageDTO
        if (conversation.getMessages() != null && !conversation.getMessages().isEmpty()) {
            dto.setMessages(
                    conversation.getMessages().stream()
                            .map(MessageDTO::fromEntityWithoutConversation)
                            .collect(Collectors.toList())
            );
        }

        // Format dates
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        if (conversation.getCreatedAt() != null) {
            dto.setCreatedAt(conversation.getCreatedAt().format(formatter));
        }

        if (conversation.getUpdatedAt() != null) {
            dto.setUpdatedAt(conversation.getUpdatedAt().format(formatter));
        }

        return dto;
    }
}
