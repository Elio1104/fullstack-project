import {User} from './user.model';

export interface FriendRequest {
  id: number;
  senderId: User;
  receiverId: User;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
}
