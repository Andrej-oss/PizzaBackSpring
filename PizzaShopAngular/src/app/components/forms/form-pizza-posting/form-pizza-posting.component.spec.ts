import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormPizzaPostingComponent } from './form-pizza-posting.component';

describe('FormPizzaPostingComponent', () => {
  let component: FormPizzaPostingComponent;
  let fixture: ComponentFixture<FormPizzaPostingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormPizzaPostingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormPizzaPostingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
