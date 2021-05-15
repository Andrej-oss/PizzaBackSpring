import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngredientAdminPageComponent } from './ingredient-admin-page.component';

describe('IngredientAdminPageComponent', () => {
  let component: IngredientAdminPageComponent;
  let fixture: ComponentFixture<IngredientAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IngredientAdminPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IngredientAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
