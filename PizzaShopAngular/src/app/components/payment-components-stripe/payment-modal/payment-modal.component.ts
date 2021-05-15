import {Component, Input, OnInit} from '@angular/core';
import {PaymentService} from '../../../logic/services/paymentDao/payment.service';
import {ToastrService} from 'ngx-toastr';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Purchase} from '../../models/Purchase';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import { Router } from '@angular/router';
import {SnackBarComponent} from '../../snack-bar/snack-bar-login/snack-bar.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserActionsService} from '../../../logic/store/actions/user/user-actions.service';


@Component({
  selector: 'app-payment-modal',
  templateUrl: './payment-modal.component.html',
  styleUrls: ['./payment-modal.component.css']
})
export class PaymentModalComponent implements OnInit {
  @Input() id;
  @Input() tittle;
  @Input() description;
  @Input() price;
  @Input() pizzaId;
  @Input() allCart;
  @Input() volume;
  @Input() drinkId;
  @Input() snackId;
  @Input() dessertId;
  purchase: Purchase;
  constructor(private paymentService: PaymentService,
              private userActionsService: UserActionsService,
              private toastrService: ToastrService,
              public activeModal: NgbActiveModal,
              private themeObjectService: ThemeObjectService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
  }

  confirm(id: string): void {
    if (!(this.allCart && this.allCart.length)) {
      this.purchase = {
        description: this.description,
        name: this.tittle,
        pizzaId: this.pizzaId,
        drinkId: this.drinkId,
        snackId: this.snackId,
        dessertId: this.dessertId,
        price: this.price,
        userId: this.themeObjectService.data.value.userId,
        volume: typeof this.volume === 'string' ? +this.volume.match(/[0-9]/gi).join('') : this.volume,
      };
      this.paymentService.confirm(id, this.purchase).subscribe(data => {
          this.activeModal.close();
          // tslint:disable-next-line:no-shadowed-variable
          this.toastrService.success
          ('Your payment is success, thank you', 'Payment success' + data[`id`],
            {positionClass: 'toast-center-center', timeOut: 3000});
          this.themeObjectService.data.value.message = `Thank you for a payment!!!`;
          this.snackBar.openFromComponent(SnackBarComponent, {
            duration: 2000,
          });
        },
        err => {
          console.log(err);
          this.activeModal.close();
        }
      );
    } else {
      this.paymentService.confirmAllCart(id, this.themeObjectService.data.value.userId, this.allCart)
        .subscribe(data => {
          this.activeModal.close();
          // tslint:disable-next-line:no-shadowed-variable
          this.router.navigate(['/']).then(data => console.log(data));
          this.allCart.forEach(value => {
            this.userActionsService.deletePizzaCartInStore(value.id);
          });
          this.toastrService.success
          ('Your payment is success, thank you', 'Payment success' + data[`id`],
            {positionClass: 'toast-center-center', timeOut: 3000});
          this.themeObjectService.data.value.message = `Thank you for a payment!!!`;
          this.snackBar.openFromComponent(SnackBarComponent, {
            duration: 2000,
          });
        });
    }
  }

  cancel(id: string): void {
    this.paymentService.cancel(id).subscribe(
      data => {
        this.toastrService.show
        ('page canceled', 'canceling page by id' + data[`id`], {
          positionClass: 'toast-top-center',
          timeOut: 3000
        });
        this.activeModal.close();
      },
      err => {
        console.log(err);
        this.activeModal.close();
      }
    );
  }
}
