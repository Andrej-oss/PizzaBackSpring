import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormDessertComponent } from './form-dessert.component';

describe('FormDessertComponent', () => {
  let component: FormDessertComponent;
  let fixture: ComponentFixture<FormDessertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormDessertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormDessertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
