import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUserRegistrationComponent } from './form-user-registration.component';

describe('FormUserRegistrationComponent', () => {
  let component: FormUserRegistrationComponent;
  let fixture: ComponentFixture<FormUserRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormUserRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormUserRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
