package technofutur.backend.api.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import technofutur.backend.api.user.UserGraphQLDTO;
import technofutur.backend.dal.entities.message.Message;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private UserGraphQLDTO sender;
    private ConversationDTO conversation;
    private String content;
    private String createdAt;
    private String updatedAt;

    public static MessageDTO fromEntity(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setSender(UserGraphQLDTO.fromEntities(message.getSender(), null));
        dto.setContent(message.getContent());

        if (message.getConversation() != null) {
            ConversationDTO conversationDTO = new ConversationDTO();
            conversationDTO.setId(message.getConversation().getId());
            dto.setConversation(conversationDTO);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        if (message.getCreatedAt() != null) {
            dto.setCreatedAt(message.getCreatedAt().format(formatter));
        }

        if (message.getUpdatedAt() != null) {
            dto.setUpdatedAt(message.getUpdatedAt().format(formatter));
        }

        return dto;
    }

    public static MessageDTO fromEntityWithoutConversation(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setSender(UserGraphQLDTO.fromEntities(message.getSender(), null));
        dto.setContent(message.getContent());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        if (message.getCreatedAt() != null) {
            dto.setCreatedAt(message.getCreatedAt().format(formatter));
        }

        if (message.getUpdatedAt() != null) {
            dto.setUpdatedAt(message.getUpdatedAt().format(formatter));
        }

        return dto;
    }
}
