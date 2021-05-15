import { Action } from '@ngrx/store';
import {User} from '../../../components/models/User';
import {Cart} from '../../../components/models/Cart';
import {Purchase} from '../../../components/models/Purchase';
import {Comment} from '../../../components/models/Comment';
import {PurchasePage} from '../../../components/models/PurchasePage';
import {Avatar} from '../../../components/models/Avatar';

export enum UserActionsTypes {
  loadedUsers = '[USERS] loaded',
  loadedPrincipal = '[PRINCIPAL] loaded',
  loadedCart = '[CART] user`s loaded',
  incrementAmountPizzaCart = '[Increment] amount pizza cart',
  decrementAmountPizzaCart = '[Decrement] amount pizza cart',
  deletePizzaCart = '[Delete] amount pizza cart',
  purchasesUserLoaded = '[PURCHASE] user loaded',
  deletePurchase = '[Purchase] delete',
  commentsUserLoaded = '[COMMENTS] user loaded',
  purchasesAllLoaded = '[PURCHASES] all loaded',
  avatarSaveLoaded = '[AVATAR] user save load',
  avatarsLoaded = '[AVATARS] all loaded',
}
export class UsersLoad implements  Action {
  readonly type = UserActionsTypes.loadedUsers;

  constructor(public payload: User[]) {
  }
}
export class PrincipalLoad implements Action {
  readonly type = UserActionsTypes.loadedPrincipal;

  constructor(public payload: User){
  }
}
export class CartLoad implements Action{
  readonly type = UserActionsTypes.loadedCart;

  constructor(public payload: Cart[]) {
  }
}
export class IncAmountPizzaCart {
  readonly type = UserActionsTypes.incrementAmountPizzaCart;

  constructor(public payload: { cart1: Cart; id: number }) {
  }
}
export class DecAmountPizzaCart {
  readonly type = UserActionsTypes.decrementAmountPizzaCart;

  constructor(public payload: { id: number; cart1: Cart }) {
  }
}
export class DeletePizzaCart {
  readonly type = UserActionsTypes.deletePizzaCart;

  constructor(public payload: { id: number }) {
  }
}
export class PurchasesByUserLoad implements Action{
  readonly type = UserActionsTypes.purchasesUserLoaded;

  constructor(public payload: Purchase[]) {
  }
}
export class DeletePurchase implements Action{
  readonly type = UserActionsTypes.deletePurchase;

  constructor(public payload: {id: number}) {
  }
}
export class CommentUsersLoad implements Action{
  readonly type = UserActionsTypes.commentsUserLoaded;

  constructor(public payload: Comment[]) {
  }
}
export class PurchasesAllLoad implements Action{
  readonly type = UserActionsTypes.purchasesAllLoaded;

  constructor(public payload: PurchasePage) {
  }
}
export class SaveUpdateAvatarUser implements Action{
  readonly type = UserActionsTypes.avatarSaveLoaded;
  constructor(public payload: Avatar) {
  }
}
export class AvatarsAllLoad implements Action{
  readonly type = UserActionsTypes.avatarsLoaded;
  constructor(public payload: Avatar[]) {
  }
}
export type UserActions = UsersLoad
  | PrincipalLoad
  | CartLoad
  | IncAmountPizzaCart
  | DecAmountPizzaCart
  | DeletePizzaCart
  | PurchasesByUserLoad
  | DeletePurchase
  | CommentUsersLoad
  | PurchasesAllLoad
  | SaveUpdateAvatarUser
  | AvatarsAllLoad;

