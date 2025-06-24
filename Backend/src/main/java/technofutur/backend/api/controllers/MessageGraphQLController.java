package technofutur.backend.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import technofutur.backend.api.message.ConversationDTO;
import technofutur.backend.api.message.MessageDTO;
import technofutur.backend.bll.services.message.MessageService;
import technofutur.backend.dal.entities.message.Conversation;
import technofutur.backend.dal.entities.message.Message;
import technofutur.backend.dal.entities.security.UserAuth;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageGraphQLController {
    private final MessageService messageService;

    private UserAuth getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAuth) {
            return (UserAuth) authentication.getPrincipal();
        }
        throw new IllegalStateException("User not authenticated");
    }

    @QueryMapping
    public List<ConversationDTO> userConversations() {
        UserAuth authenticatedUser = getAuthenticatedUser();
        return messageService.getUserConversations(authenticatedUser.getId())
                .stream()
                .map(ConversationDTO::fromEntity)
                .toList();
    }

    @MutationMapping
    public MessageDTO sendMessage(@Argument Long receiverId, @Argument String content) {
        UserAuth authenticatedUser = getAuthenticatedUser();
        Message message = messageService.sendMessage(authenticatedUser.getId(), receiverId, content);
        return MessageDTO.fromEntity(message);

    }
}
