import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Ingredient} from '../../models/Ingredient';
import {IngredientService} from '../../../logic/services/ingredientDao/ingredient.service';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {Observable} from 'rxjs';
import {select, Store} from '@ngrx/store';
import {IngredientsSelector} from '../../../logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-form-ingredient-posting',
  templateUrl: './form-ingredient-posting.component.html',
  styleUrls: ['./form-ingredient-posting.component.css']
})
export class FormIngredientPostingComponent implements OnInit {
  @Input()
  ingredient: Ingredient;
  ingredients: Observable<Ingredient[]> = this.store$.pipe(select(IngredientsSelector));
  ingredientFormGroup: FormGroup;
  name: FormControl;
  price: FormControl;
  image: FormControl = new FormControl('', Validators.required);
  formData: FormData = new FormData();
  constructor(private ingredientService: IngredientService,
              private store$: Store,
              public themeObjectService: ThemeObjectService,
              private pizzaActionService: PizzaActionService) {
  }

  ngOnInit(): void {
    this.ingredientFormGroup = new FormGroup({
      name: this.name = new FormControl(this.ingredient ? this.ingredient.name : '', Validators.required),
      price: this.price = new FormControl(this.ingredient ? this.ingredient.price : '', Validators.required),
      image: this.image
    });
  }

  saveIngredient(ingredientFormGroup: FormGroup): void{
    this.formData.append('name', ingredientFormGroup.controls.name.value);
    this.formData.append('price', ingredientFormGroup.controls.price.value);
    this.pizzaActionService.saveIngredient(this.formData,
      this.formData.append('image', ingredientFormGroup.controls.image.value));
    this.ingredientFormGroup.reset();
    this.clearFormData();
  }

  upLoadFile(event): void {
    const file = (event.target as HTMLInputElement).files[0];
    this.ingredientFormGroup.patchValue({
      image: file
    });
    this.ingredientFormGroup.get('image').updateValueAndValidity();
  }
  clearFormData(): void{
    this.formData.delete('name');
    this.formData.delete('price');
    this.formData.delete('image');
  }

  updateIngredient(ingredientFormGroup: FormGroup): void{
    this.formData.append('name', ingredientFormGroup.controls.name.value);
    this.formData.append('price', ingredientFormGroup.controls.price.value);
    this.pizzaActionService.updateAndGetIngredients(this.ingredient.id, this.formData,
      this.formData.append('image', ingredientFormGroup.controls.image.value));
    this.clearFormData();
  }
}
