import { Component, OnInit } from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {Drink} from '../../models/Drink';
import {DrinksSelector} from '../../../logic/store/selectors/PizzaSelector';
import {Cart} from '../../models/Cart';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {APiURL} from "../../../config/urlConfig";
import {UserService} from "../../../logic/services/userDao/user.service";

@Component({
  selector: 'app-drink-choose-sheet',
  templateUrl: './drink-choose-sheet.component.html',
  styleUrls: ['./drink-choose-sheet.component.css']
})
export class DrinkChooseSheetComponent implements OnInit {
  drinks: Drink[];
  url = APiURL.drinkImage;
  isPaymentOpen: boolean;
  cart: Cart;
  drinkChoose: Drink;
  constructor(public themeObjectService: ThemeObjectService,
              private userActionsService: UserActionsService,
              private userService: UserService,
              private store$: Store) { }

  ngOnInit(): void {
    this.store$.pipe(select(DrinksSelector)).subscribe(data => this.drinks = data);
  }

  saveDrinkInCart(drink: Drink): void{
    this.cart = {
      description: drink.name,
      drinkId: drink.id,
      amount: 1,
      price: drink.price,
      userId: this.themeObjectService.data.value.userId,
      volume: drink.volume,
    };
    this.themeObjectService.data.value.message = 'Drink added to cart';
    if (this.userService.isAuthenticated()) {
      this.userActionsService.saveElementInCart(this.cart);
    } else {
      this.userService.saveCartInLocalStorage(this.cart);
    }
  }

  openPayment(drink: Drink): void{
    console.log(drink);
    this.isPaymentOpen = !this.isPaymentOpen;
    this.drinkChoose = drink;
  }
}
