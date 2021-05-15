import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSnackComponent } from './form-snack.component';

describe('FormSnackComponent', () => {
  let component: FormSnackComponent;
  let fixture: ComponentFixture<FormSnackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormSnackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormSnackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
