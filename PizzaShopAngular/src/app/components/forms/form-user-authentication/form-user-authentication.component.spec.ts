import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormUserAuthenticationComponent } from './form-user-authentication.component';

describe('FormUserAuthenticationComponent', () => {
  let component: FormUserAuthenticationComponent;
  let fixture: ComponentFixture<FormUserAuthenticationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormUserAuthenticationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormUserAuthenticationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
