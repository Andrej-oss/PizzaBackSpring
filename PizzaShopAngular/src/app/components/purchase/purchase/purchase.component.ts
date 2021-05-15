import {Component, Input, OnInit} from '@angular/core';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {Purchase} from '../../models/Purchase';
import {selectPurchases} from '../../../logic/store/selectors/UserSelect';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {Cart} from '../../models/Cart';
import {UserService} from '../../../logic/services/userDao/user.service';
import {options} from '../../../constants/Constants';
import {Drink} from '../../models/Drink';
import {DessertSelector, DrinksSelector, SnacksSelector} from '../../../logic/store/selectors/PizzaSelector';
import {Snack} from '../../models/Snack';
import {Dessert} from '../../models/Dessert';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.css']
})
export class PurchaseComponent implements OnInit {
  @Input()
  type: string;
  options: { name: string, value: string }[];
  drinks: Drink[];
  snacks: Snack[];
  desserts: Dessert[];
  source: string;
  purchases$: Observable<Purchase[]> = this.store$.pipe(select(selectPurchases));
  purchasesUser: Purchase[];
  displayedColumns: string[] = ['position', 'name', 'description', 'size', 'count', 'price', 'date', 'option'];
  blackTheme = 'purchase-item-black';
  whiteTheme = 'purchase-item';
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black; ';
  cart: Cart;
  isUser: boolean;
  selectedValue: string;
  constructor(private store$: Store,
              private userActionsService: UserActionsService,
              private userService: UserService,
              public themeObjectService: ThemeObjectService) {
  }

  ngOnInit(): void {
    this.source = 'purchase';
    this.isUser = this.userService.isUser();
    this.options = options;
    this.store$.pipe(select(DessertSelector)).subscribe(data => this.desserts = data);
    this.store$.pipe(select(DrinksSelector)).subscribe(data => this.drinks = data);
    this.store$.pipe(select(SnacksSelector)).subscribe(data => this.snacks = data);
    this.store$.pipe(select(selectPurchases)).subscribe(data => this.purchasesUser = data);
    if (!this.purchasesUser.length){
      this.userActionsService.getPurchasesByUser(this.themeObjectService.data.value.userId);
    }
  }

  onDelete(id: any): void {
    this.userActionsService.deletePurchaseInStore(id);
  }

  saveInCart(id: number,
             drinkIdItem: number,
             dessertIdItem: number,
             descriptionPurchase: string,
             name: string,
             amountItem: number,
             priceItem: number,
             volumeItem: number): void {
    if (id !== 0) {
      this.cart = {
        description: descriptionPurchase,
        pizzaId: id,
        amount: amountItem,
        price: priceItem,
        userId: this.themeObjectService.data.value.userId,
        size: name,
        volume: volumeItem
      };
    }
    if (drinkIdItem !== 0) {
      this.cart = {
        description: descriptionPurchase,
        drinkId: drinkIdItem,
        amount: amountItem,
        price: priceItem,
        userId: this.themeObjectService.data.value.userId,
        size: name,
        volume: volumeItem
      };
    }
    if (dessertIdItem !== 0) {
      this.cart = {
        description: descriptionPurchase,
        dessertId: dessertIdItem,
        amount: amountItem,
        price: priceItem,
        userId: this.themeObjectService.data.value.userId,
        size: name,
        volume: volumeItem
      };
    }
    console.log(this.cart);
    this.themeObjectService.data.value.message = 'Item added to cart';
    this.userActionsService.saveElementInCart(this.cart);
  }

  onSortPurchase(value: string): void {
    const optionsArray = value.split(', ');
    this.userActionsService.getAllPurchases(0, optionsArray[0], optionsArray[1]);
  }

  findDessertVolume(dessertId: number): string{
    const dessert = this.desserts.find(value => dessertId === value.id);
    return dessert.volume;
  }

  findSnackVolume(snackId: number): string{
    const snack = this.snacks.find(value => snackId === value.id);
    return snack.volume ;
  }
}
