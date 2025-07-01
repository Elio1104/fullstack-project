import {User} from '../../user/models/user.model';
import {Message} from 'postcss';

export interface Conversation {
  id: number;
  users: User[];
  messages: Message[];
  createdAt: Date;
  updatedAt: Date;
}
