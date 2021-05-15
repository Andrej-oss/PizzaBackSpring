import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PizzaChooseSheetComponent } from './pizza-choose-sheet.component';

describe('PizzaChooseSheetComponent', () => {
  let component: PizzaChooseSheetComponent;
  let fixture: ComponentFixture<PizzaChooseSheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PizzaChooseSheetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PizzaChooseSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
