import {User} from '../../user/models/user.model';
import {Conversation} from './conversation.model';

export interface Message {
  id: number;
  senderId: User;
  conversation: Conversation;
  content: string;
  createdAt: Date;
  updatedAt: Date;
}
