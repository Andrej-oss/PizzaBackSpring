import { Component, OnInit } from '@angular/core';
import {PizzaActionService} from '../../logic/store/actions/pizza/pizza-action.service';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {select, Store} from '@ngrx/store';
import {Pizza} from '../models/Pizza';
import {Promotion} from '../models/Promotion';
import {Drink} from '../models/Drink';
import {Snack} from '../models/Snack';
import {Dessert} from '../models/Dessert';
import {
  AllPizzasSelector, DessertSelector,
  DrinksSelector,
  PromotionsSelector,
  SnacksSelector
} from '../../logic/store/selectors/PizzaSelector';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  pizzas: Pizza[];
  promotions: Promotion[];
  drinks: Drink[];
  snacks: Snack[];
  desserts: Dessert[];
  url = '/api/promotion/';
  constructor(private pizzaActionService: PizzaActionService,
              private store$: Store,
              private router: Router,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
    this.store$.pipe(select(AllPizzasSelector)).subscribe(data => this.pizzas = data);
    this.store$.pipe(select(PromotionsSelector)).subscribe(data => this.promotions = data);
    this.store$.pipe(select(DrinksSelector)).subscribe(data => this.drinks = data);
    this.store$.pipe(select(SnacksSelector)).subscribe(data => this.snacks = data);
    this.store$.pipe(select(DessertSelector)).subscribe(data => this.desserts = data);
    if (!this.pizzas.length){
      this.pizzaActionService.getAllPizzas();
    }
    if (!this.promotions.length){
      this.pizzaActionService.getPromotions();
    }
    if (!this.drinks.length){
      this.pizzaActionService.getDrinks();
    }
    if (!this.snacks.length){
      this.pizzaActionService.getAllSnacks();
    }
    if (!this.desserts.length){
      this.pizzaActionService.getAllDessert();
    }
  }

  onPizzas(): void{
    this.router.navigate(['pizza']).then(data => console.log(data));
  }

  onDrinks(): void{
    this.router.navigate(['drinks']).then(data => console.log(data));
  }

  onSnacks(): void{
    this.router.navigate(['snacks']).then(data => console.log(data));
  }

  onDesserts(): void{
    this.router.navigate(['desserts']).then(data => console.log(data));
  }

  onChangeProportoin(): boolean{
    return window.screen.width > 800 ? true : false;
  }
}
