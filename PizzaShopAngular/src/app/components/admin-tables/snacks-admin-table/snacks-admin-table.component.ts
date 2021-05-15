import {Component, Input, OnInit} from '@angular/core';
import {select, Store} from '@ngrx/store';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Snack} from '../../models/Snack';
import {Observable} from 'rxjs';
import {SnacksSelector} from '../../../logic/store/selectors/PizzaSelector';
import {PizzaActionService} from "../../../logic/store/actions/pizza/pizza-action.service";


@Component({
  selector: 'app-snacks-admin-table',
  templateUrl: './snacks-admin-table.component.html',
  styleUrls: ['./snacks-admin-table.component.css']
})
export class SnacksAdminTableComponent implements OnInit {
  snacks: Observable<Snack[]> = this.store$.pipe(select(SnacksSelector));
  displayedColumns: string[] = ['position', 'image', 'name', 'description', 'volume', 'price', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  url = '/api/snack/';
  isOpenSnackUpdate: boolean;
  snack: Snack;
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: number): void{
    this.pizzaActionService.deleteSnack(id);
  }

  upDate(element: Snack): void{
    this.isOpenSnackUpdate = !this.isOpenSnackUpdate;
    this.snack = element;
  }
}
