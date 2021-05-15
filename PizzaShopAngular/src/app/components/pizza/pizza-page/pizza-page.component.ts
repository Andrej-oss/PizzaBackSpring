import {Component, Input, OnInit} from '@angular/core';
import {Pizza} from '../../models/Pizza';
import {select, Store} from '@ngrx/store';
import {Observable, Subscription} from 'rxjs';
import {AllPizzasSelector, PromotionsSelector} from '../../../logic/store/selectors/PizzaSelector';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {Avatar} from '../../models/Avatar';
import {selectAllAvatars} from '../../../logic/store/selectors/UserSelect';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';
import {Promotion} from '../../models/Promotion';
import {pizzaOptions} from '../../../constants/Constants';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';


@Component({
  selector: 'app-pizza-page',
  templateUrl: './pizza-page.component.html',
  styleUrls: ['./pizza-page.component.css']
})
export class PizzaPageComponent implements OnInit {
  @Input()
  count: number;
  pizzas: Pizza[];
  options = pizzaOptions;
  sub: Subscription;
  avatars: Avatar[];
  blackTheme = 'purchase-item-black';
  whiteTheme = 'purchase-item';
  selectedValue: string;
  source: string;
  blackColor = 'color: white';
  whiteColor = 'color: black; ';
  activePage: number;
  url = '/api/promotion/';
  promotions: Observable<Promotion[]> = this.store$.pipe(select(PromotionsSelector));
  constructor(private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService,
              private userService: UserActionsService,
              private store$: Store) { }

  ngOnInit(): void {
    this.sub = this.store$.pipe(select(AllPizzasSelector)).subscribe(data => this.pizzas = data);
    this.sub = this.store$.pipe(select(selectAllAvatars)).subscribe(data => this.avatars = data);
    if (this.pizzas.length === 0 || this.pizzas.length > 10) {
      this.pizzaActionService.getSortedPizzas(0, 'asc', 'newPizza');
      this.pizzaActionService.getIngredients();
    }
    if (!this.avatars.length){
      this.userService.getAllAvatars();
    }
    this.source = 'pizza';
  }

  onSortPizza(value: string): void{
    const optionsArray = value.split(', ');
    this.pizzaActionService.getSortedPizzas(0, optionsArray[1], optionsArray[0]);
    this.activePage = 1;
  }
}
