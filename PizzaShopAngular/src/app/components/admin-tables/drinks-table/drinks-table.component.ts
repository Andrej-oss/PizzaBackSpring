import {Component, Input, OnInit} from '@angular/core';
import {Drink} from '../../models/Drink';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-drinks-table',
  templateUrl: './drinks-table.component.html',
  styleUrls: ['./drinks-table.component.css']
})
export class DrinksTableComponent implements OnInit {
  @Input()
  drinks: Drink[];
  displayedColumns: string[] = ['position', 'image', 'name', 'volume', 'price', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  url = '/api/drink/';
  isOpenDrinkUpdate: boolean;
  drink: Drink;
  constructor(public themeObjectService: ThemeObjectService,
              private pizzaActionService: PizzaActionService) { }

  ngOnInit(): void {
  }

  onDelete(id: number): void{
    this.pizzaActionService.deleteDrink(id);
  }

  upDate(element: Drink): void{
    this.isOpenDrinkUpdate = !this.isOpenDrinkUpdate;
    this.drink = element;
  }
}
