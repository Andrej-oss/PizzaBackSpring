import {Component, Input, OnInit} from '@angular/core';
import {MatBottomSheetRef} from '@angular/material/bottom-sheet';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {select, Store} from '@ngrx/store';
import {Observable, Subscription} from 'rxjs';
import {Pizza} from '../../models/Pizza';
import {
  AllPizzasSelector,
  CommentSelector,
  IngredientsSelector,
  SizePizzaSelector
} from '../../../logic/store/selectors/PizzaSelector';
import {Ingredient} from '../../models/Ingredient';
import {Size} from '../../models/Size';
import {Cart} from '../../models/Cart';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {Comment} from '../../models/Comment';
import {CartService} from '../../../logic/services/cartDao/cart.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-pizza-choose-sheet',
  templateUrl: './pizza-choose-sheet.component.html',
  styleUrls: ['./pizza-choose-sheet.component.css']
})
export class PizzaChooseSheetComponent implements OnInit {
  diameter: number;
  url = '/api/pizza/image/';
  sizeUrl = '/api/size/image/';
  cart: Cart;
  classSize = 'pizza-card-image-content';
  size: Observable<Size> = this.store$.pipe(select(SizePizzaSelector));
  comments: Observable<Comment[]> = this.store$.pipe(select(CommentSelector));
  ingredients: Observable<Ingredient[]> = this.store$.pipe(select(IngredientsSelector));
  pizzaSize: string;
  currentSize: Size;
  pizzas: Pizza[];
  pizza: Pizza;
  subscription: Subscription;
  pizzaName: string[];
  isActive = false;
  isAddPrice1: boolean;
  isAddPrice2: boolean;
  isAddPrice3: boolean;
  isAddName: boolean;
  isOpenCommentForm: boolean;
  isOpenComments: boolean;
  isPaymentOpen: boolean;
  constructor(private bottomSheetRef: MatBottomSheetRef<PizzaChooseSheetComponent>,
              public themeObjectService: ThemeObjectService,
              private pizzaService: PizzaActionService,
              private cartService: CartService,
              private userActionsService: UserActionsService,
              private snackBar: MatSnackBar,
              private store$: Store) {
  }

  ngOnInit(): void {
    this.subscription = this.store$.pipe(select(AllPizzasSelector)).subscribe(data => this.pizzas = data);
    const index = this.pizzas.findIndex(value => value.id === this.themeObjectService.data.value.idChoosePizza);
    this.pizza = this.pizzas[index];
    this.pizzaName = this.pizza.description.split(',');
    this.isAddName = false;
    this.isAddPrice1 = false;
    this.isAddPrice2 = false;
    this.isAddPrice3 = false;
    this.pizzaSize = 'small';
    this.store$.pipe(select(SizePizzaSelector)).subscribe(data => this.currentSize = data);
  }

  onAdd(price: number, name: string, i: number): void {
    debugger;
    if (i === 0 && !this.isAddPrice1) {
      this.isAddPrice1 = !this.isAddPrice1;
      this.themeObjectService.data.value.price = this.themeObjectService.data.value.price + price;
      this.isActive = !this.isActive;
      this.pizzaName.push(name);
    } else if (i === 0 && this.isAddPrice1) {
      this.isAddPrice1 = !this.isAddPrice1;
      this.themeObjectService.data.value.price = this.themeObjectService.data.value.price - price;
      // @ts-ignore
      this.isActive = !this.isActive;
      this.pizzaName = this.pizzaName.filter(value => value !== name);
    } else if (i === 1 && !this.isAddPrice2) {
      this.isAddPrice2 = !this.isAddPrice2;
      this.themeObjectService.data.value.price = this.themeObjectService.data.value.price + price;
      this.pizzaName.push(name);
    } else if (i === 1 && this.isAddPrice2) {
      this.isAddPrice2 = !this.isAddPrice2;
      this.themeObjectService.data.value.price = this.themeObjectService.data.value.price - price;
      this.pizzaName = this.pizzaName.filter(value => value !== name);
    } else if (i === 2 && !this.isAddPrice3) {
      this.isAddPrice3 = !this.isAddPrice3;
      this.themeObjectService.data.value.price = this.themeObjectService.data.value.price + price;
      this.pizzaName.push(name);
    } else if (i === 2 && this.isAddPrice3) {
      this.isAddPrice3 = !this.isAddPrice3;
      this.themeObjectService.data.value.price = this.themeObjectService.data.value.price - price;
      this.pizzaName = this.pizzaName.filter(value => value !== name);
    }
  }

  onLargePizza(id: number): void {
    this.pizzaService.getSizePizza(id, 'large');
    this.classSize = 'pizza-card-image-content-large';
    this.isAddPrice1 = false;
    this.isAddPrice2 = false;
    this.isAddPrice3 = false;
    this.pizzaName = this.pizza.description.split(',');
    this.pizzaSize = 'large';
    this.store$.pipe(select(SizePizzaSelector)).subscribe(data => this.currentSize = data);
  }

  onMediumPizza(id: number): void {
    this.pizzaService.getSizePizza(id, 'medium');
    this.classSize = 'pizza-card-image-content-medium';
    this.isAddPrice1 = false;
    this.isAddPrice2 = false;
    this.isAddPrice3 = false;
    this.pizzaName = this.pizza.description.split(',');
    this.pizzaSize = 'medium';
    this.store$.pipe(select(SizePizzaSelector)).subscribe(data => this.currentSize = data);
  }

  onSmallPizza(id: number): void {
    this.pizzaService.getSizePizza(id, 'small');
    this.classSize = 'pizza-card-image-content';
    this.isAddPrice1 = false;
    this.isAddPrice2 = false;
    this.isAddPrice3 = false;
    this.pizzaName = this.pizza.description.split(',');
    this.pizzaSize = 'small';
    this.store$.pipe(select(SizePizzaSelector)).subscribe(data => this.currentSize = data);
  }

  savePizzaInCart(id: number): void {
    this.cart = {
      description: this.pizzaName.join(', '),
      pizzaId: id,
      amount: 1,
      price: this.themeObjectService.data.value.price,
      volume: this.currentSize.weight,
      userId: this.themeObjectService.data.value.userId,
      size: this.pizzaSize,
    };
    this.themeObjectService.data.value.message = 'Pizza added to cart';
    this.userActionsService.saveElementInCart(this.cart);
  }

  openCommentFrom(): void{
    this.isOpenCommentForm = !this.isOpenCommentForm;
  }

  openComments(): void{
    this.isOpenComments = !this.isOpenComments;
  }

  openPayment(): void{
    this.isPaymentOpen = !this.isPaymentOpen;
  }
}
