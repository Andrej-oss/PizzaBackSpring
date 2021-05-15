import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackChooseSheetComponent } from './snack-choose-sheet.component';

describe('SnackChooseSheetComponent', () => {
  let component: SnackChooseSheetComponent;
  let fixture: ComponentFixture<SnackChooseSheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnackChooseSheetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SnackChooseSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
