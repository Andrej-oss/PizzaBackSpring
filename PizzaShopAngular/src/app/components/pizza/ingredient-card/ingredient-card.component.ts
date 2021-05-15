import {Component, Input, OnInit} from '@angular/core';
import {Ingredient} from '../../models/Ingredient';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';

@Component({
  selector: 'app-ingredient-card',
  templateUrl: './ingredient-card.component.html',
  styleUrls: ['./ingredient-card.component.css']
})
export class IngredientCardComponent implements OnInit {
@Input()
ingredient: Ingredient;
@Input()
isActive: boolean;
  url = '/api/ingredient/image/';

  constructor(public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

}
