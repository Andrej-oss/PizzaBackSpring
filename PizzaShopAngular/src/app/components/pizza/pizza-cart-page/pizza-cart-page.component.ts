import { Component, OnInit } from '@angular/core';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {Pizza} from '../../models/Pizza';
import {
  AllPizzasSelector,
  DessertSelector,
  DrinksSelector,
  SnacksSelector
} from '../../../logic/store/selectors/PizzaSelector';
import {Cart} from '../../models/Cart';
import {selectCart, selectPrincipal} from '../../../logic/store/selectors/UserSelect';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {User} from '../../models/User';
import {Drink} from '../../models/Drink';
import {Snack} from '../../models/Snack';
import {Dessert} from '../../models/Dessert';

@Component({
  selector: 'app-pizza-cart-page',
  templateUrl: './pizza-cart-page.component.html',
  styleUrls: ['./pizza-cart-page.component.css']
})
export class PizzaCartPageComponent implements OnInit {
  pizzas: Observable<Pizza[]> = this.store$.pipe(select(AllPizzasSelector));
  cartElements: Observable<Cart[]> = this.store$.pipe(select(selectCart));
  principal: Observable<User> = this.store$.pipe(select(selectPrincipal));
  drinks: Observable<Drink[]> = this.store$.pipe(select(DrinksSelector));
  snacks: Observable<Snack[]> = this.store$.pipe(select(SnacksSelector));
  desserts: Observable<Dessert[]> = this.store$.pipe(select(DessertSelector));
  constructor(private store$: Store,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
    this.themeObjectService.data.value.isOpenPayment = false;
  }

  onPayment(): void{
    debugger;
    this.themeObjectService.data.value.isOpenPayment = true;
  }
}
