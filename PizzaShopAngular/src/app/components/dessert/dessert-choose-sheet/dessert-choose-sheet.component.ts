import { Component, OnInit } from '@angular/core';
import {Dessert} from '../../models/Dessert';
import {Cart} from '../../models/Cart';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {select, Store} from '@ngrx/store';
import {DessertSelector} from '../../../logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-dessert-choose-sheet',
  templateUrl: './dessert-choose-sheet.component.html',
  styleUrls: ['./dessert-choose-sheet.component.css']
})
export class DessertChooseSheetComponent implements OnInit {
  desserts: Dessert[];
  url = '/api/dessert/';
  isPaymentOpen: boolean;
  cart: Cart;
  dessertChoose: Dessert;
  constructor(public themeObjectService: ThemeObjectService,
              private userActionsService: UserActionsService,
              private store$: Store) { }

  ngOnInit(): void {
    this.store$.pipe(select(DessertSelector)).subscribe(data => this.desserts = data);
  }

  saveDessertInCart(dessert: Dessert): void{
    this.cart = {
      description: dessert.name,
      dessertId: dessert.id,
      amount: 1,
      price: dessert.price,
      userId: this.themeObjectService.data.value.userId,
      volume: +dessert.volume.match(/[0-9]/gi).join('') + 0.00,
    };
    this.themeObjectService.data.value.message = 'Dessert added to cart';
    this.userActionsService.saveElementInCart(this.cart);
  }

  openPayment(dessert: Dessert): void{
    this.isPaymentOpen = !this.isPaymentOpen;
    this.dessertChoose = dessert;
  }
}
