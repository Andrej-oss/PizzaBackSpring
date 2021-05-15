import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Dessert} from '../../models/Dessert';
import {select, Store} from '@ngrx/store';
import {DessertSelector} from '../../../logic/store/selectors/PizzaSelector';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-dessert-table',
  templateUrl: './dessert-table.component.html',
  styleUrls: ['./dessert-table.component.css']
})
export class DessertTableComponent implements OnInit {
  desserts: Observable<Dessert[]> = this.store$.pipe(select(DessertSelector));
  url = '/api/dessert/';
  displayedColumns: string[] = ['position', 'image', 'name', 'description', 'volume', 'price', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  isOpenDessertUpdate: boolean;
  dessert: Dessert;
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: number): void{
    this.pizzaActionService.deleteDessert(id);
  }

  upDate(element: Dessert): void{
    this.isOpenDessertUpdate = !this.isOpenDessertUpdate;
    this.dessert = element;
  }
}
