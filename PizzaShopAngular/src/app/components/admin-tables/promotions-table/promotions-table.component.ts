import { Component, OnInit } from '@angular/core';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {Promotion} from '../../models/Promotion';
import {PromotionsSelector} from '../../../logic/store/selectors/PizzaSelector';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-promotions-table',
  templateUrl: './promotions-table.component.html',
  styleUrls: ['./promotions-table.component.css']
})
export class PromotionsTableComponent implements OnInit {
  blackColor = 'color: white';
  whiteColor = 'color: black';
  black = 'background-color: black';
  white = 'background-color: white';
  displayedColumns: string[] = ['position', 'image', 'name', 'option'];
  url = '/api/promotion/';
  promotion: Promotion;
  isOpenPromotionUpdater: boolean;
  promotions: Observable<Promotion[]> = this.store$.pipe(select(PromotionsSelector));
  constructor(private store$: Store,
              private pizzaActionService: PizzaActionService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onDelete(id: number): void{
    this.pizzaActionService.deletePromotion(id);
  }

  upDate(element: Promotion): void{
    this.isOpenPromotionUpdater = !this.isOpenPromotionUpdater;
    this.promotion = element;
  }
}
