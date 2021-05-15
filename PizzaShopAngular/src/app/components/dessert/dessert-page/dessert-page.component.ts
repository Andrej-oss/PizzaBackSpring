import {Component, Input, OnInit} from '@angular/core';
import {Dessert} from '../../models/Dessert';
import {select, Store} from '@ngrx/store';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {DessertSelector} from '../../../logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-dessert-page',
  templateUrl: './dessert-page.component.html',
  styleUrls: ['./dessert-page.component.css']
})
export class DessertPageComponent implements OnInit {
  @Input()
  count: number;
  desserts: Dessert[];
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) {
    this.store$.pipe(select(DessertSelector)).subscribe(data => this.desserts = data);
  }

  ngOnInit(): void {
    if (!this.desserts.length){
      this.pizzaActionService.getAllDessert();
    }
  }
}
