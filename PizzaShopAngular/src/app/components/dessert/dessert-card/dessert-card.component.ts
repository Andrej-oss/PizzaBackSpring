import {Component, Input, OnInit} from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Dessert} from '../../models/Dessert';
import {MatBottomSheet} from '@angular/material/bottom-sheet';
import {DessertChooseSheetComponent} from '../dessert-choose-sheet/dessert-choose-sheet.component';
import {Cart} from '../../models/Cart';
import {UserActionsService} from "../../../logic/store/actions/user/user-actions.service";

@Component({
  selector: 'app-dessert-card',
  templateUrl: './dessert-card.component.html',
  styleUrls: ['./dessert-card.component.css']
})
export class DessertCardComponent implements OnInit {
  @Input()
  dessert: Dessert;
  cart: Cart;
  url = '/api/dessert/';
  constructor(private bottomSheet: MatBottomSheet,
              private userActionsService: UserActionsService,
              public themeObjectService: ThemeObjectService) { }

  ngOnInit(): void {
  }

  onChooseDrink(id: number): void{
    this.themeObjectService.data.value.idChooseDessert = id;
    this.bottomSheet.open(DessertChooseSheetComponent);
  }

  onToCartDessert(dessert: Dessert): void{
    this.cart = {
      description: dessert.name,
      dessertId: dessert.id,
      amount: 1,
      price: dessert.price,
      userId: this.themeObjectService.data.value.userId,
      volume: +dessert.volume.match(/[0-9]/gi).join('') + 0.00,
    };
    this.themeObjectService.data.value.message = 'Dessert added to cart';
    this.userActionsService.saveElementInCart(this.cart);
  }
}
