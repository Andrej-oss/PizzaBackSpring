import {Component, Input, OnInit} from '@angular/core';
import {select, Store} from '@ngrx/store';
import {Snack} from '../../models/Snack';
import {SnacksSelector} from '../../../logic/store/selectors/PizzaSelector';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-snack-page',
  templateUrl: './snack-page.component.html',
  styleUrls: ['./snack-page.component.css']
})
export class SnackPageComponent implements OnInit {
  @Input()
  count: number;
  snacks: Snack[];

  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService) { }

  ngOnInit(): void {
    this.store$.pipe(select(SnacksSelector)).subscribe(data => this.snacks = data);
    if (!this.snacks.length){
      this.pizzaActionService.getAllSnacks();
    }
  }

}
