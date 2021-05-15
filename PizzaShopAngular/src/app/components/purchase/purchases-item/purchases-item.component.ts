import {Component, Input, OnInit} from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {select, Store} from '@ngrx/store';
import { Subscription} from 'rxjs';
import {Pizza} from '../../models/Pizza';
import {
  AllPizzasSelector, DessertSelector,
  DrinksSelector,
  SnacksSelector
} from '../../../logic/store/selectors/PizzaSelector';
import {Drink} from '../../models/Drink';
import {Snack} from '../../models/Snack';
import {Dessert} from '../../models/Dessert';

@Component({
  selector: 'app-purchases-item',
  templateUrl: './purchases-item.component.html',
  styleUrls: ['./purchases-item.component.css']
})
export class PurchasesItemComponent implements OnInit {
  @Input() pizzaId: number;
  @Input() drinkId: number;
  @Input() snackId: number;
  @Input() dessertId: number;
  url = '/api/pizza/image/';
  urlDrink = '/api/drink/';
  urlSnack = '/api/snack/';
  urlDessert = '/api/dessert/';
  pizzas: Pizza[];
  drinks: Drink[];
  snacks: Snack[];
  desserts: Dessert[];
  item: Pizza | Drink | Snack | Dessert;
  sub: Subscription;
  constructor(public themeObjectService: ThemeObjectService,
              private store$: Store) { }

  ngOnInit(): void {
    this.sub = this.store$.pipe(select(AllPizzasSelector))
      .subscribe(data => this.pizzas = data);
    this.sub = this.store$.pipe(select(DrinksSelector))
      .subscribe(data => this.drinks = data);
    this.sub = this.store$.pipe(select(SnacksSelector))
      .subscribe(data => this.snacks = data);
    this.sub = this.store$.pipe(select(DessertSelector)).
      subscribe(data => this.desserts = data);
    this.findItem();
  }
  findItem(): void{
    if (this.pizzaId !== 0){
      this.item = this.pizzas.find(value => value.id === +this.pizzaId);
    }
    else if (this.drinkId !== 0){
      this.item = this.drinks.find(value => value.id === +this.drinkId);
    }
    else if (this.snackId !== 0){
      this.item = this.snacks.find(value => value.id === +this.snackId);
    }
    else if (this.dessertId !== 0){
      this.item = this.desserts.find(value => this.dessertId === value.id);
    }
  }
}
