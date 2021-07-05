import {Component, Input, OnInit} from '@angular/core';
import {Cart} from '../../models/Cart';
import {Pizza} from '../../models/Pizza';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Drink} from '../../models/Drink';
import {Snack} from '../../models/Snack';
import {Dessert} from '../../models/Dessert';
import {UserService} from "../../../logic/services/userDao/user.service";
import {CartService} from "../../../logic/services/cartDao/cart.service";
import {PizzaActionService} from "../../../logic/store/actions/pizza/pizza-action.service";


@Component({
  selector: 'app-pizza-cart-card',
  templateUrl: './pizza-cart-card.component.html',
  styleUrls: ['./pizza-cart-card.component.css']
})
export class PizzaCartCardComponent implements OnInit {
  @Input() cartElements: Cart[];
  @Input() pizzas: Pizza[];
  @Input() drinks: Drink[];
  @Input() snacks: Snack[];
  @Input() desserts: Dessert[];
  pizzasPrise: {id: number, price: number}[] = [];
  totalPrice: number;

  constructor(public themeObjectService: ThemeObjectService,
              private userService: UserService,
              private pizzaActionService: PizzaActionService,
              private cartService: CartService) {
  }

  ngOnInit(): void {
    this.totalPrice = 0;
    this.themeObjectService.data.value.totalPrice = 0;
    if (!this.userService.isAuthenticated()){
      const cartFromLocalStorage = this.cartService.getCartFromLocalStorage();
      if (cartFromLocalStorage.length){
        this.cartElements = cartFromLocalStorage;
      }
    }
    this.getTotalPrice();
    this.cartElements.forEach(value => {
      if (value.pizzaId > 0) {
        const pizzaPrice = {id: value.id, price: value.price};
        this.pizzasPrise.push(pizzaPrice);
      }
    });
    if (!this.pizzas.length) {
      this.pizzaActionService.getAllPizzas();
    }
    if (!this.drinks.length) {
      this.pizzaActionService.getDrinks();
    }
    if (!this.snacks.length) {
      this.pizzaActionService.getAllSnacks();
    }
    if (!this.desserts.length) {
      this.pizzaActionService.getAllDessert();
    }
  }

  getTotalPrice(): void {
    this.totalPrice = +this.cartElements.reduce((previousValue, currentValue) => {
      return previousValue + currentValue.price;
    }, 0);
    this.themeObjectService.data.value.totalPrice = this.totalPrice;
  }

  onDeleteCartElement(description: string): void{
    const index = this.cartElements.findIndex(value => value.description === description);
    const element = this.cartElements[index];
    this.themeObjectService.data.value.totalPrice = this.themeObjectService.data.value.totalPrice - element.price;
    this.cartElements.splice(index, 1);
  }
}
