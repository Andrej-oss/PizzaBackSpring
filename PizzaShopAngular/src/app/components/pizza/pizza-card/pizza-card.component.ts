import {Component, Input, OnInit} from '@angular/core';
import {Pizza} from '../../models/Pizza';
import {MatBottomSheet} from '@angular/material/bottom-sheet';
import {PizzaChooseSheetComponent} from '../pizza-choose-sheet/pizza-choose-sheet.component';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';



@Component({
  selector: 'app-pizza-card',
  templateUrl: './pizza-card.component.html',
  styleUrls: ['./pizza-card.component.css']
})
export class PizzaCardComponent implements OnInit {
@Input()
pizza: Pizza;
  url = '/api/pizza/image/';
  ingredients: string[];

  constructor(private bottomSheet: MatBottomSheet,
              private themeObjectService: ThemeObjectService,
              private pizzaService: PizzaActionService) { }

  ngOnInit(): void {
    this.ingredients = this.pizza.ingredients.split(',');
    // tslint:disable-next-line:radix
    this.themeObjectService.data.value.ingredients = this.ingredients.map(data => parseInt(data));
  }

  onChoosePizza(id: number): void{
    this.themeObjectService.data.value.idChoosePizza = id;
    this.bottomSheet.open(PizzaChooseSheetComponent);
    this.pizzaService.getSizePizza(id, 'small');
    this.pizzaService.getPizzaComments(id);
  }

}
