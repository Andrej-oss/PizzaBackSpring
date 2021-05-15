import {Component, Input, OnInit} from '@angular/core';
import {select, Store} from '@ngrx/store';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Observable} from 'rxjs';
import {Size} from '../../models/Size';
import {SizesPizzaSelector} from '../../../logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-pizza-size-admin-table',
  templateUrl: './pizza-size-admin-table.component.html',
  styleUrls: ['./pizza-size-admin-table.component.css']
})
export class PizzaSizeAdminTableComponent implements OnInit {
  @Input()
  pizzaName: string;
  sizes$: Observable<Size[]> = this.store$.pipe(select(SizesPizzaSelector));
  displayedColumns: string[] = ['position', 'image', 'name', 'weight', 'diameter', 'pizza', 'price', 'option'];
  black = 'background-color: black';
  white = 'background-color: white';
  blackColor = 'color: white';
  whiteColor = 'color: black';
  url = '/api/size/image/';
  isOpenSizeUpdate: boolean;
  size: Size;
  isOpenSizeCreator: boolean;
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: any): void{
    this.pizzaActionService.deletePizzaSize(id);
  }

  upDate(element: any): void{
    this.isOpenSizeUpdate = !this.isOpenSizeUpdate;
    this.size = element;
  }

  onPizzaSizeCreator(): void{
    this.isOpenSizeCreator = !this.isOpenSizeCreator;
  }
}
