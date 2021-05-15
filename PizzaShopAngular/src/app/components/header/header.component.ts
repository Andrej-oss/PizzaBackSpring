import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {Observable, of} from 'rxjs';
import {Cart} from '../models/Cart';
import {select, Store} from '@ngrx/store';
import {selectCart} from '../../logic/store/selectors/UserSelect';
import {Pizza} from '../models/Pizza';
import {
  AllPizzasSelector,
  DessertSelector,
  DrinksSelector,
  SnacksSelector
} from '../../logic/store/selectors/PizzaSelector';
import {UserService} from '../../logic/services/userDao/user.service';
import {Drink} from '../models/Drink';
import {Dessert} from '../models/Dessert';
import {Snack} from '../models/Snack';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isCartOpen: boolean;
  isCartOpened: boolean;
  cartElements: Observable<Cart[]> = this.store$.pipe(select(selectCart));
  pizzas: Observable<Pizza[]> = this.store$.pipe(select(AllPizzasSelector));
  drinks: Observable<Drink[]> =  this.store$.pipe(select(DrinksSelector));
  snacks: Observable<Snack[]> = this.store$.pipe(select(SnacksSelector));
  desserts: Observable<Dessert[]> = this.store$.pipe(select(DessertSelector));
  constructor(private router: Router,
              public userService: UserService,
              private store$: Store,
              public themeSubjectService: ThemeObjectService) {
  }

  ngOnInit(): void {
    this.isCartOpen = false;
    this.isCartOpened = false;
  }

  onLogin(): void {
    this.router.navigateByUrl('/authenticate').then(data => console.log(data));
  }

  onRegistration(): void {
    this.router.navigateByUrl('/registration').then(data => console.log(data));
  }

  onAdmin(): void {
    this.router.navigateByUrl('/admin').then(data => console.log(data));
  }

  onUserPage(): void {
    this.router.navigateByUrl('/user_page').then(data => console.log(data));
  }

  onDark(): void {
    this.themeSubjectService.data.value.isDarkTheme = !this.themeSubjectService.data.value.isDarkTheme;
  }

  showCart(b: boolean): void {
    this.isCartOpened = b;
    if (this.themeSubjectService.data.value.sizeCart !== 0 && !this.isCartOpened) {
      setTimeout(() => {
        this.isCartOpen = true;
      }, 1000);
    }
  }

  hideCart(b: boolean): void {
    this.isCartOpened = b;
    if (this.themeSubjectService.data.value.sizeCart !== 0) {
      setTimeout(() => {
        if (!this.isCartOpened){
          this.isCartOpen = false;
          this.isCartOpened = false;
        }
        else {
          this.isCartOpen = true;
          this.isCartOpened = true;
        }
      }, 1000);
    }
  }

  onCartPage(): void {
    if (this.themeSubjectService.data.value.sizeCart > 0) {
      this.router.navigate(['/cart']).then(data => console.log(data));
    }
  }

  showCartNow(b: boolean): void{
    this.isCartOpen = true;
    this.isCartOpened = true;
  }

  onHome(): void{
    this.router.navigate(['/']).then(data => console.log(data));
  }

  isMobile(): boolean{
    return window.screen.width < 700 ? true : false;
  }
}
