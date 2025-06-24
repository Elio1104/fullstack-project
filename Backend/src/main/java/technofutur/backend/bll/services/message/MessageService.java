package technofutur.backend.bll.services.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import technofutur.backend.bll.services.security.UserService;
import technofutur.backend.dal.entities.message.Conversation;
import technofutur.backend.dal.entities.message.Message;
import technofutur.backend.dal.entities.security.UserAuth;
import technofutur.backend.dal.repositories.message.ConversationRepository;
import technofutur.backend.dal.repositories.message.MessageRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public Conversation getOrCreateConversation(Long senderId, Long receiverId) {
        UserAuth sender = userService.loadUserById(senderId);
        UserAuth receiver = userService.loadUserById(receiverId);

        return conversationRepository
                .findByParticipantsContainingAndParticipantsContaining(sender, receiver)
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.getParticipants().add(sender);
                    conversation.getParticipants().add(receiver);
                    return conversationRepository.save(conversation);
                });
    }

    public List<Conversation> getUserConversations(Long userId) {
        UserAuth user = userService.loadUserById(userId);
        return conversationRepository.findByParticipantsContaining(user);
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        UserAuth sender = userService.loadUserById(senderId);
        Conversation conversation = getOrCreateConversation(senderId, receiverId);

        Message message = new Message();
        message.setSender(sender);
        message.setConversation(conversation);
        message.setContent(content);

        return messageRepository.save(message);
    }
}
