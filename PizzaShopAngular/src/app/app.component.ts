import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ThemeObjectService} from './logic/theme-object/theme-object.service';
import {UserService} from './logic/services/userDao/user.service';
import {PizzaActionService} from './logic/store/actions/pizza/pizza-action.service';
import {select, Store} from '@ngrx/store';
import {async, Observable} from 'rxjs';
import {Promotion} from './components/models/Promotion';
import {PromotionsSelector} from './logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'pizza-shop';
  darkTheme = 'app-content-dark';
  whiteTheme = 'app-content';
  promotions: Promotion[];
  constructor(public userService: UserService,
              private router: Router,
              private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeSubjectService: ThemeObjectService) {
    this.store$.pipe(select(PromotionsSelector)).subscribe(data => this.promotions = data);
  }

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    // if (token != null){
    //   this.userService.setToken(token);
    // }
    if (!this.promotions.length) {
    this.pizzaActionService.getPromotions();
    }
  }

  addIngredient(): void {
    this.router.navigateByUrl('/ingredient_post').then(r => console.log(r));
  }

  addPizza(): void{
    this.router.navigateByUrl('/pizza_post').then(r => console.log(r));
  }
}
