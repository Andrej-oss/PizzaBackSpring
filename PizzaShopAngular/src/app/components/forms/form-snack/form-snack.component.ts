import {Component, Input, OnInit} from '@angular/core';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {Store} from '@ngrx/store';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Snack} from '../../models/Snack';

@Component({
  selector: 'app-form-snack',
  templateUrl: './form-snack.component.html',
  styleUrls: ['./form-snack.component.css']
})
export class FormSnackComponent implements OnInit {
  @Input()
  snack: Snack;
  snackForm: FormGroup;
  nameSnack: FormControl;
  descriptionSnack: FormControl;
  priceSnack: FormControl;
  volumeSnack: FormControl;
  image: File;
  formData: FormData = new FormData();

  constructor(public themeObjectService: ThemeObjectService,
              private store$: Store,
              private pizzaActionService: PizzaActionService) {
  }

  ngOnInit(): void {
    this.snackForm = new FormGroup({
      nameSnack: this.nameSnack = new FormControl(this.snack ? this.snack.name : '', Validators.required),
      descriptionSnack: this.descriptionSnack = new FormControl(this.snack ? this.snack.description : '', Validators.required),
      priceSnack: this.priceSnack = new FormControl(this.snack ? this.snack.price : '', Validators.required),
      volumeSnack: this.volumeSnack = new FormControl(this.snack ? this.snack.volume : '', Validators.required)
    });
  }

  upLoadFileSize($event: Event): void {
    this.image = ($event.target as HTMLInputElement).files[0];
  }

  onSave(snackForm: FormGroup): void {
    this.formData.append('name', snackForm.controls.nameSnack.value);
    this.formData.append('description', snackForm.controls.descriptionSnack.value);
    this.formData.append('price', snackForm.controls.priceSnack.value);
    this.formData.append('volume', snackForm.controls.volumeSnack.value);
    const path = (snackForm.controls.nameSnack.value).split(' ').map(value => {
      console.log(value.charAt(0).toUpperCase());
      const strings = value.split('');
      strings.splice(0, 1, value.charAt(0).toUpperCase());
      return strings.join('');
    });
    this.formData.append('path', path);
    this.pizzaActionService.saveSnack(this.formData, this.formData.append('image', this.image));
    this.reset();
  }

  onUpdate(snackForm: FormGroup): void {
    this.formData.append('name', snackForm.controls.nameSnack.value);
    this.formData.append('description', snackForm.controls.descriptionSnack.value);
    this.formData.append('price', snackForm.controls.priceSnack.value);
    this.formData.append('volume', snackForm.controls.volumeSnack.value);
    const path = (snackForm.controls.nameSnack.value).split(' ').map(value => {
      console.log(value.charAt(0).toUpperCase());
      const strings = value.split('');
      strings.splice(0, 1, value.charAt(0).toUpperCase());
      return strings.join('');
    });
    this.formData.append('path', path);
    this.pizzaActionService.updateSnack(this.snack.id, this.formData, this.formData.append('image', this.image));
    this.reset();
  }
  reset(): void{
    this.formData.delete('name');
    this.formData.delete('description');
    this.formData.delete('price');
    this.formData.delete('path');
    this.formData.delete('image');
    this.formData.delete('volume');
    this.snackForm.reset();
  }
}
