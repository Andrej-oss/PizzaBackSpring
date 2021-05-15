import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Ingredient} from '../../models/Ingredient';
import {select, Store} from '@ngrx/store';
import {IngredientsSelector} from '../../../logic/store/selectors/PizzaSelector';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-ingredient-admin-page',
  templateUrl: './ingredient-admin-page.component.html',
  styleUrls: ['./ingredient-admin-page.component.css']
})
export class IngredientAdminPageComponent implements OnInit {
ingredients$: Observable<Ingredient[]> = this.store$.pipe(select(IngredientsSelector));
  ingredient: Ingredient;
  displayedColumns: string[] = ['position', 'image', 'name', 'price', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  url = '/api/ingredient/image/';
  isOpenIngredientUpdater: boolean;
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: any): void{
    this.pizzaActionService.deleteIngredient(id);
  }

  upDate(element: any): void{
    this.ingredient = element;
    this.isOpenIngredientUpdater = !this.isOpenIngredientUpdater;
  }
}
