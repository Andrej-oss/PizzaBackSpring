import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import {
  CartLoad,
  PrincipalLoad,
  UsersLoad,
  IncAmountPizzaCart,
  DecAmountPizzaCart,
  DeletePizzaCart,
  PurchasesByUserLoad,
  DeletePurchase,
  CommentUsersLoad,
  PurchasesAllLoad,
  SaveUpdateAvatarUser,
  AvatarsAllLoad
} from '../../actions-type/userActions';
import {ThemeObjectService} from '../../../theme-object/theme-object.service';
import {Cart} from '../../../../components/models/Cart';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SnackBarComponent} from '../../../../components/snack-bar/snack-bar-login/snack-bar.component';
import {PurchaseService} from '../../../services/purchaseDao/purchase.service';
import {CartService} from '../../../services/cartDao/cart.service';
import {CommentService} from '../../../services/commentDao/comment.service';
import {UserService} from '../../../services/userDao/user.service';
import {AvatarService} from '../../../services/avatarDao/avatar.service';
import {User} from '../../../../components/models/User';



@Injectable({
  providedIn: 'root'
})
export class UserActionsService {

  constructor(private userService: UserService,
              private purchaseService: PurchaseService,
              private store: Store,
              private commentService: CommentService,
              private cartService: CartService,
              private snackBar: MatSnackBar,
              private avatarService: AvatarService,
              public themeObjectService: ThemeObjectService) { }

   getUsers(): | {}{
    return  this.userService.getAllUsers().subscribe( data => {
      return this.store.dispatch(new UsersLoad(data));
    });
   }
   getPrincipal(name: string): | {}{
    return this.userService.getUserByName(name).subscribe( data => {
      this.themeObjectService.data.value.userName = data.username;
      this.themeObjectService.data.value.userId = data.id;
      debugger;
      return this.store.dispatch(new PrincipalLoad(data));
     });
   }
   upDatePrincipal(id: number, user: User): | {}{
    return this.userService.updateUser(id, user)
      .subscribe(data => {
        this.themeObjectService.data.value.message = 'User updated';
        this.snackBar.openFromComponent(SnackBarComponent, {
          duration: 2000,
        });
        return this.store.dispatch(new PrincipalLoad(data));
      });
   }
   deleteUser(id: number): | {}{
    return this.userService.deleteUser(id)
      .subscribe(data => {
        this.themeObjectService.data.value.message = 'User deleted';
        this.snackBar.openFromComponent(SnackBarComponent, {
          duration: 2000,
        });
        return this.store.dispatch(new UsersLoad(data));
      });
   }
   getAllCart(id: number): | {}{
    return this.cartService.getAllCartsElements(id)
      .subscribe(data => {
        this.themeObjectService.data.value.sizeCart = data.length;
        return this.store.dispatch(new CartLoad(data));
      });
   }
   getUsersComments(name: string): | {}{
    return this.commentService.getCommentsByUserId(name)
      .subscribe(data => this.store.dispatch(new CommentUsersLoad(data)));
   }
  saveElementInCart(cart: Cart): | {}{
    return this.cartService.savePizzaInCart(cart)
      .subscribe(data => {
        this.themeObjectService.data.value.sizeCart = data.length;
        this.snackBar.openFromComponent(SnackBarComponent, {
          duration: 2000,
        });
        return this.store.dispatch(new CartLoad(data));
      });
  }
  incAmountPizzaCartInStore(id: number, cart1: Cart): | void{
    return this.store.dispatch(new IncAmountPizzaCart({id, cart1}));
  }
  decAmountPizzaCartInStore(id: number, cart1: Cart): void{
    this.store.dispatch(new DecAmountPizzaCart({id, cart1}));
  }
  deletePizzaCartInStore(id: number): void{
    this.store.dispatch(new DeletePizzaCart({id}));
    this.themeObjectService.data.value.sizeCart = this.themeObjectService.data.value.sizeCart - 1;
  }
  getPurchasesByUser(id: number): | {}{
    return this.purchaseService.getPurchasesByUser(id)
      .subscribe(data => this.store.dispatch(new PurchasesByUserLoad(data)));
  }
  getAllPurchases(page: number, sort: string, type: string): | {}{
    return this.purchaseService.getAllPurchases(page, sort, type)
      .subscribe(data => {
        this.themeObjectService.data.value.firstPage = data.number + 1;
        this.themeObjectService.data.value.lastPage = data.totalPages;
        this.themeObjectService.data.value.sort = sort;
        this.themeObjectService.data.value.type = type;
        return this.store.dispatch(new PurchasesAllLoad(data));
      });
  }
  deletePurchaseInStore(id: number): |{}{
    return this.purchaseService.deletePurchase(id)
      .subscribe(data => {
        if (data === true){
          this.store.dispatch(new DeletePurchase({id}));
        }
      });
  }
  saveAvatarUser(userId: number, formData: FormData, append: void): | {}{
    return this.avatarService.saveAvatar(userId, formData, append)
      .subscribe(data => this.store.dispatch(new SaveUpdateAvatarUser(data)));
  }
  getUserAvatar(userId: number): | {}{
    return this.avatarService.getAvatar(userId)
      .subscribe(data => this.store.dispatch(new SaveUpdateAvatarUser(data)));
  }
  getAllAvatars(): |{}{
    return this.avatarService.getAllAvatars()
      .subscribe(data => this.store.dispatch(new AvatarsAllLoad(data)))
  }
}
