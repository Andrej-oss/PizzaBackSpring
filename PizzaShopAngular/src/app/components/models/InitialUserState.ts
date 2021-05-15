import {User} from './User';
import {Cart} from './Cart';
import {Purchase} from './Purchase';
import {Comment} from './Comment';
import {Avatar} from './Avatar';

export interface DefaultUserStore {
  users: User[];
  principal: User;
  cart: Cart[];
  purchases: Purchase[];
  comments: Comment[];
  avatar: Avatar;
  avatars: Avatar[];
}

export const InitialUserState: DefaultUserStore =  {
  users: [],
  principal: null,
  cart: [],
  purchases: [],
  comments: [],
  avatar: null,
  avatars: [],
};
