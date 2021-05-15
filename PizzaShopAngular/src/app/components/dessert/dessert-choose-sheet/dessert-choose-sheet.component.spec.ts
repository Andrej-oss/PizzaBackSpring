import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DessertChooseSheetComponent } from './dessert-choose-sheet.component';

describe('DessertChooseSheetComponent', () => {
  let component: DessertChooseSheetComponent;
  let fixture: ComponentFixture<DessertChooseSheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DessertChooseSheetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DessertChooseSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
