import {Component, Input, OnInit} from '@angular/core';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {UserActionsService} from '../../logic/store/actions/user/user-actions.service';
import {PizzaActionService} from '../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  @Input()
  source: string;
pages: {id: number}[];
@Input()
activePage: number;
  constructor(public themeObjectService: ThemeObjectService,
              private pizzaActionService: PizzaActionService,
              private userActionsService: UserActionsService) { }

  ngOnInit(): void {
    this.pages = Array.from(Array(this.themeObjectService.data.value.lastPage), (v: {id: number}, i) => {
      return {id: i + 1};
    });
    this.activePage = 1;
  }

  onChangePage(id: number): void{
    if (this.activePage !== id && this.source === 'purchase') {
      this.userActionsService.getAllPurchases(id - 1, this.themeObjectService.data.value.sort, this.themeObjectService.data.value.type);
    }
    else if (this.source === 'pizza' && this.activePage !== id){
      this.pizzaActionService.getSortedPizzas(id - 1, this.themeObjectService.data.value.type, this.themeObjectService.data.value.sort);
    }
    this.activePage = id;
  }

  onNext(pages: { id: number }[]): void{
    this.pages = pages.map(value => {
      if (value.id <= this.themeObjectService.data.value.lastPage) {
        return {
          id: value.id + 10
        };
      }
    });
  }

  onPrevious(pages: { id: number }[]): void{
    this.pages = pages.map(value => {
      if (value.id > 10) {
        return {
          id: value.id - 10
        };
      }
    });
  }
}
