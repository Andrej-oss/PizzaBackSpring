import { Pipe, PipeTransform } from '@angular/core';
import {Drink} from '../../components/models/Drink';

@Pipe({
  name: 'volumeDrink'
})
export class VolumeDrinkPipe implements PipeTransform {

  transform(value: number, args: Drink[]): number {
    console.log(value);
    if (args && value) {
      const drink = args.find(value1 => value1.id === value);
      return drink.volume;
    }
  }
}
