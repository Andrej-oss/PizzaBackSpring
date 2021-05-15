import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordLoginReminderComponent } from './password-login-reminder.component';

describe('PasswordLoginReminderComponent', () => {
  let component: PasswordLoginReminderComponent;
  let fixture: ComponentFixture<PasswordLoginReminderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordLoginReminderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordLoginReminderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
