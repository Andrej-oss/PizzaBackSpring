import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackBarRegistrationComponent } from './snack-bar-registration.component';

describe('SnackBarRegistrationComponent', () => {
  let component: SnackBarRegistrationComponent;
  let fixture: ComponentFixture<SnackBarRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnackBarRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SnackBarRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
