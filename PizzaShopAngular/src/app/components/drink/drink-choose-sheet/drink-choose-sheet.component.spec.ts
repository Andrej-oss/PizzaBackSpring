import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinkChooseSheetComponent } from './drink-choose-sheet.component';

describe('DrinkChooseSheetComponent', () => {
  let component: DrinkChooseSheetComponent;
  let fixture: ComponentFixture<DrinkChooseSheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrinkChooseSheetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinkChooseSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
