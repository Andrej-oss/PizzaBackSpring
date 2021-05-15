import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {select, Store} from '@ngrx/store';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {AllPizzasSelector} from '../../../logic/store/selectors/PizzaSelector';
import {Pizza} from '../../models/Pizza';
import {Rating} from '../../models/Rating';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-pizza-admin-table',
  templateUrl: './pizza-admin-table.component.html',
  styleUrls: ['./pizza-admin-table.component.css']
})
export class PizzaAdminTableComponent implements OnInit {
  pizzas$: Observable<Pizza[]> = this.store$.pipe(select(AllPizzasSelector));
  pizza: Pizza;
  displayedColumns: string[] = ['position', 'image', 'new', 'name', 'description', 'price', 'rating', 'ordersCount', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  url = '/api/pizza/image/';
  isOpenPizzaUpdater: boolean;
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }
  getRating(rating: Rating[]): number{
    return rating && rating.length > 0 ? rating
      .reduce((previousValue, currentValue) => previousValue + currentValue.value, 0) / rating.length
      : 0;
  }

  onDelete(id: number): void{
    this.pizzaActionService.deletePizza(id);
  }

  upDate(element: Pizza): void{
    this.pizza = element;
    this.isOpenPizzaUpdater = !this.isOpenPizzaUpdater;
  }
}
