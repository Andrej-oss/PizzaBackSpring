import {Component, Input, OnInit} from '@angular/core';
import {select, Store} from '@ngrx/store';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Drink} from '../../models/Drink';
import {DrinksSelector} from '../../../logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-drink-page',
  templateUrl: './drink-page.component.html',
  styleUrls: ['./drink-page.component.css']
})
export class DrinkPageComponent implements OnInit {
  @Input()
  count: number;
drinks: Drink[];
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) {
    this.store$.pipe(select(DrinksSelector)).subscribe(data => this.drinks = data);
  }

  ngOnInit(): void {
    if (!this.drinks.length){
      this.pizzaActionService.getDrinks();
    }
  }

}
