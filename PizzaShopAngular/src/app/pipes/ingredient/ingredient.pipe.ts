import { Pipe, PipeTransform } from '@angular/core';
import {Ingredient} from '../../components/models/Ingredient';

@Pipe({
  name: 'ingredient'
})
export class IngredientPipe implements PipeTransform {

  transform(value: string, args: Ingredient[]): string {
    if (args) {
      const ingredient = args.find(value1 => value1.id === +value);
      return ingredient.name;
    }
  }
}
