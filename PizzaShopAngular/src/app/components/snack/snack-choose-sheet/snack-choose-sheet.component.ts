import { Component, OnInit } from '@angular/core';
import {Cart} from '../../models/Cart';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {select, Store} from '@ngrx/store';
import {SnacksSelector} from '../../../logic/store/selectors/PizzaSelector';
import {Snack} from '../../models/Snack';

@Component({
  selector: 'app-snack-choose-sheet',
  templateUrl: './snack-choose-sheet.component.html',
  styleUrls: ['./snack-choose-sheet.component.css']
})
export class SnackChooseSheetComponent implements OnInit {
  snacks: Snack[];
  url = '/api/snack/';
  isPaymentOpen: boolean;
  cart: Cart;
  snackChoose: Snack;
  constructor(public themeObjectService: ThemeObjectService,
              private userActionsService: UserActionsService,
              private store$: Store) { }

  ngOnInit(): void {
    debugger;
    this.store$.pipe(select(SnacksSelector)).subscribe(data => this.snacks = data);
  }

  saveDrinkInCart(snack: Snack): void{
    this.cart = {
      description: snack.description,
      snackId: snack.id,
      amount: 1,
      price: snack.price,
      userId: this.themeObjectService.data.value.userId,
      volume: +snack.volume.match(/[0-9]/gi).join('') + 0.00,
    };
    console.log(this.cart);
    this.themeObjectService.data.value.message = 'Snack added to cart';
    this.userActionsService.saveElementInCart(this.cart);
  }

  openPayment(snack: Snack): void{
    this.isPaymentOpen = !this.isPaymentOpen;
    this.snackChoose = snack;
  }
}
