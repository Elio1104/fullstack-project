import {FriendRequest} from './friendRequest.model';

export interface User {
  id : number,
  username : string,
  firstName : string,
  lastName : string,
  email : string,
  role: string,
  friends: User[],
  friendRequestsReceived: FriendRequest[],
  friendRequestsSent: FriendRequest[],
}
