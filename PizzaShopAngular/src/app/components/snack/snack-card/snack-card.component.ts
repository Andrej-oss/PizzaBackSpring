import {Component, Input, OnInit} from '@angular/core';
import {Snack} from '../../models/Snack';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {MatBottomSheet} from '@angular/material/bottom-sheet';
import {SnackChooseSheetComponent} from '../snack-choose-sheet/snack-choose-sheet.component';
import {Cart} from '../../models/Cart';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';

@Component({
  selector: 'app-snack-card',
  templateUrl: './snack-card.component.html',
  styleUrls: ['./snack-card.component.css']
})
export class SnackCardComponent implements OnInit {
  @Input()
  snack: Snack;
  cart: Cart;
  url = '/api/snack/';
  constructor(public themeObjectService: ThemeObjectService,
              private userActionsService: UserActionsService,
              private bottomSheet: MatBottomSheet) { }

  ngOnInit(): void {
  }

  onChooseSnack(id: number): void{
    this.themeObjectService.data.value.idChooseSnack = id;
    this.bottomSheet.open(SnackChooseSheetComponent);
  }

  onToCart(snack: Snack): void{
    this.cart = {
      description: snack.description,
      snackId: snack.id,
      amount: 1,
      price: snack.price,
      userId: this.themeObjectService.data.value.userId,
      volume: +snack.volume.match(/[0-9]/gi).join('') + 0.00,
    };
    this.themeObjectService.data.value.message = 'Snack added to cart';
    this.userActionsService.saveElementInCart(this.cart);
  }
}
