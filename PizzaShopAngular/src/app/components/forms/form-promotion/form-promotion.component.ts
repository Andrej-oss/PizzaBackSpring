import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Promotion} from '../../models/Promotion';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-form-promotion',
  templateUrl: './form-promotion.component.html',
  styleUrls: ['./form-promotion.component.css']
})
export class FormPromotionComponent implements OnInit {
  @Input()
  promotion: Promotion;
  promotionForm: FormGroup;
  promotionName: FormControl;
  image: File;
  promotionFormData: FormData;
  constructor(public themeObjectService: ThemeObjectService,
              private pizzaActionService: PizzaActionService) {
    this.promotionFormData = new FormData();
  }

  ngOnInit(): void {
    this.promotionForm = new FormGroup({
      promotionName: this.promotionName = new FormControl(this.promotion ? this.promotion.name : '', [Validators.required])
    });
  }

  upLoadFile($event: Event): void{
    this.image = ($event.target as HTMLInputElement).files[0];
  }

  savePromotion(promotionForm: FormGroup): void{
    console.log(promotionForm.controls.promotionName.value, this.image);
    this.promotionFormData.append('name', promotionForm.controls.promotionName.value);
    this.pizzaActionService.savePromotion(this.promotionFormData, this.promotionFormData.append('image', this.image));
    this.reset();
  }
  reset(): void{
    this.promotionForm.reset();
    this.promotionFormData.delete('image');
  }
}
