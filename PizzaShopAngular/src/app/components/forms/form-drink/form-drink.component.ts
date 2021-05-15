import {Component, Input, OnInit} from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Drink} from '../../models/Drink';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';

@Component({
  selector: 'app-form-drink',
  templateUrl: './form-drink.component.html',
  styleUrls: ['./form-drink.component.css']
})
export class FormDrinkComponent implements OnInit {
  @Input()
  drink: Drink;
  drinkForm: FormGroup;
  nameDrink: FormControl;
  volume: FormControl;
  priceDrink: FormControl;
  image: File;
  formData: FormData = new FormData();

  constructor(public themeObjectService: ThemeObjectService,
              private pizzaActionService: PizzaActionService) {
  }

  ngOnInit(): void {
    this.drinkForm = new FormGroup({
      nameDrink: this.nameDrink = new FormControl(this.drink ? this.drink.name : '', Validators.required),
      volume: this.volume = new FormControl(this.drink ? this.drink.volume : '', Validators.required),
      priceDrink: this.priceDrink = new FormControl(this.drink ? this.drink.price : '', Validators.required),
    });
    console.log(this.drinkForm);
  }

  upLoadFileSize($event: Event): void {
    this.image = ($event.target as HTMLInputElement).files[0];
  }

  onSave(drinkForm: FormGroup): void {
    this.formData.append('name', drinkForm.controls.nameDrink.value);
    this.formData.append('volume', drinkForm.controls.volume.value);
    this.formData.append('price', drinkForm.controls.priceDrink.value);
    this.pizzaActionService.saveDrink(this.formData, this.formData.append('image', this.image));
    this.reset();
  }

  onUpdate(drinkForm: FormGroup): void {
    this.formData.append('name', drinkForm.controls.nameDrink.value);
    this.formData.append('volume', drinkForm.controls.volume.value);
    this.formData.append('price', drinkForm.controls.priceDrink.value);
    this.pizzaActionService.updateDrink(this.drink.id, this.formData, this.formData.append('image', this.image));
    this.reset();
  }

  reset(): void {
    this.formData.delete('name');
    this.formData.delete('volume');
    this.formData.delete('price');
    this.formData.delete('image');
    this.drinkForm.reset();
  }
}
