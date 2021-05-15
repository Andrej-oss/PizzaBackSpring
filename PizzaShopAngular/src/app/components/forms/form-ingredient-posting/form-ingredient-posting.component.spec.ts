import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormIngredientPostingComponent } from './form-ingredient-posting.component';

describe('FormPizzaPostingComponent', () => {
  let component: FormIngredientPostingComponent;
  let fixture: ComponentFixture<FormIngredientPostingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormIngredientPostingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormIngredientPostingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
