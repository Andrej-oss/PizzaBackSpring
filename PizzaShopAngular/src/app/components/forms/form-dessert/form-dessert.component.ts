import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Store} from '@ngrx/store';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {Dessert} from '../../models/Dessert';

@Component({
  selector: 'app-form-dessert',
  templateUrl: './form-dessert.component.html',
  styleUrls: ['./form-dessert.component.css']
})
export class FormDessertComponent implements OnInit {
  @Input()
  dessert: Dessert;
  dessertForm: FormGroup;
  nameDessert: FormControl;
  descriptionDessert: FormControl;
  priceDessert: FormControl;
  volumeDessert: FormControl;
  image: File;
  formData: FormData = new FormData();

  constructor(public themeObjectService: ThemeObjectService,
              private store$: Store,
              private pizzaActionService: PizzaActionService) {
  }

  ngOnInit(): void {
    this.dessertForm = new FormGroup({
      nameDessert: this.nameDessert = new FormControl(this.dessert ? this.dessert.name : '', Validators.required),
      descriptionDessert: this.descriptionDessert = new FormControl(this.dessert ? this.dessert.description : '', Validators.required),
      priceDessert: this.priceDessert = new FormControl(this.dessert ? this.dessert.price : '', Validators.required),
      volumeDessert: this.volumeDessert = new FormControl(this.dessert ? this.dessert.volume : '', Validators.required)
    });
  }

  upLoadFileSize($event: Event): void {
    this.image = ($event.target as HTMLInputElement).files[0];
  }

  onSave(dessertForm: FormGroup): void {
    this.formData.append('name', dessertForm.controls.nameDessert.value);
    this.formData.append('description', dessertForm.controls.descriptionDessert.value);
    this.formData.append('price', dessertForm.controls.priceDessert.value);
    this.formData.append('volume', dessertForm.controls.volumeDessert.value);
    const path = (dessertForm.controls.nameDessert.value).split(' ').map(value => {
      const strings = value.split('');
      strings.splice(0, 1, value.charAt(0).toUpperCase());
      return strings.join('');
    });
    this.formData.append('path', path);
    this.pizzaActionService.saveDessert(this.formData, this.formData.append('image', this.image));
    this.reset();
  }

  onUpdate(dessertForm: FormGroup): void {
    this.formData.append('name', dessertForm.controls.nameDessert.value);
    this.formData.append('description', dessertForm.controls.descriptionDessert.value);
    this.formData.append('price', dessertForm.controls.priceDessert.value);
    this.formData.append('volume', dessertForm.controls.volumeDessert.value);
    const path = (dessertForm.controls.nameDessert.value).split(' ').map(value => {
      const strings = value.split('');
      strings.splice(0, 1, value.charAt(0).toUpperCase());
      return strings.join('');
    });
    this.formData.append('path', path);
    this.pizzaActionService.updateDessert(this.dessert.id, this.formData, this.formData.append('image', this.image));
    this.reset();
  }
  reset(): void{
    this.formData.delete('name');
    this.formData.delete('description');
    this.formData.delete('price');
    this.formData.delete('path');
    this.formData.delete('image');
    this.formData.delete('volume');
    this.dessertForm.reset();
  }
}
