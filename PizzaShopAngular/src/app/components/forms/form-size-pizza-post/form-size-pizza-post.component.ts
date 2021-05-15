import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Ingredient} from '../../models/Ingredient';
import {Pizza} from '../../models/Pizza';
import {sizes} from '../../../constants/Constants';
import {PizzaService} from '../../../logic/services/pizzaDao/pizza.service';
import {IngredientService} from '../../../logic/services/ingredientDao/ingredient.service';
import {SizeService} from '../../../logic/services/sizeDao/size.service';
import {Size} from '../../models/Size';
import {PizzaActionService} from '../../../logic/store/actions/pizza/pizza-action.service';
import {ThemeObjectService} from '../../../logic/theme-object/theme-object.service';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {AllPizzasSelector} from '../../../logic/store/selectors/PizzaSelector';

@Component({
  selector: 'app-form-size-pizza-post',
  templateUrl: './form-size-pizza-post.component.html',
  styleUrls: ['./form-size-pizza-post.component.css']
})
export class FormSizePizzaPostComponent implements OnInit {
  @Input()
  size: Size;
  @Input()
  pizzaName: string;
  url = '/api/pizza/image/';
  sizes = sizes;
  pizzas: Observable<Pizza[]> = this.store$.pipe(select(AllPizzasSelector));
  ingredients: Ingredient[];
  arrayIngredients: number[] = [];
  pizzaSizeForm: FormGroup;
  file: FormControl = new FormControl('', Validators.required);
  sizeFormData: FormData = new FormData();
  sizePizza: FormControl;
  priceSize: FormControl;
  pizzaId: FormControl;
  diameter: FormControl;
  weight: FormControl;
  constructor(private ingredientService: IngredientService,
              private pizzaService: PizzaService,
              private store$: Store,
              public themeObjectService: ThemeObjectService,
              private pizzaActionService: PizzaActionService,
              private sizeService: SizeService) {
  }

  ngOnInit(): void {
    this.pizzaActionService.getAllPizzas();
    this.ingredientService.getAllIngredients().subscribe(data => this.ingredients = data);
    this.pizzaSizeForm = new FormGroup({
      sizePizza: this.sizePizza  = new FormControl(this.size ? this.size.size : ''),
      priceSize: this.priceSize = new FormControl(this.size ? this.size.price : '', Validators.required),
      pizzaId: this.pizzaId = new FormControl(this.size ? this.size.pizza_id : ''),
      diameter: this.diameter = new FormControl(this.size ? this.size.diameter : '', Validators.required),
      weight: this.weight = new FormControl(this.size ? this.size.weight : '', Validators.required),
      file: this.file
    });
  }
  resetSizeFormData(): void{
    this.sizeFormData.delete('name');
    this.sizeFormData.delete('price');
    this.sizeFormData.delete('diameter');
    this.sizeFormData.delete('weight');
    this.sizeFormData.delete('file');
  }

  upLoadFileSize(event): void{
    const sizeImage = (event.target as HTMLInputElement).files[0];
    console.log(sizeImage);
    this.pizzaSizeForm.patchValue({
      file: sizeImage
    });
    this.pizzaSizeForm.updateValueAndValidity();
  }

  onAddIngredient(pizzaId: FormControl): void{
    this.arrayIngredients.push(pizzaId.value);
  }

  onSave(pizzaSizeForm: FormGroup): void {
    this.sizeFormData.append('name', pizzaSizeForm.controls.sizePizza.value.size);
    this.sizeFormData.append('price', pizzaSizeForm.controls.priceSize.value);
    this.sizeFormData.append('diameter', pizzaSizeForm.controls.sizePizza.value.diameter);
    this.sizeFormData.append('weight', pizzaSizeForm.controls.weight.value);
    this.sizeFormData.append('file',  pizzaSizeForm.controls.file.value);
    this.sizeService.saveSizePizza(this.sizeFormData,
      pizzaSizeForm.controls.pizzaId.value,
      pizzaSizeForm.controls.file.value).subscribe(data => console.log(data));
    this.resetSizeFormData();
    pizzaSizeForm.reset();
  }

  onUpdate(pizzaSizeForm: FormGroup): void{
    this.sizeFormData.append('name', this.size ? this.size.name : pizzaSizeForm.controls.sizePizza.value.size);
    this.sizeFormData.append('price', pizzaSizeForm.controls.priceSize.value);
    this.sizeFormData.append('diameter', this.size ? this.size.diameter : pizzaSizeForm.controls.sizePizza.value.diameter);
    this.sizeFormData.append('weight', pizzaSizeForm.controls.weight.value);
    this.sizeFormData.append('file',  pizzaSizeForm.controls.file.value);
    this.pizzaActionService.upgradePizzaSize(this.size.id, this.sizeFormData,
      pizzaSizeForm.controls.file.value);
    this.resetSizeFormData();
  }
}
