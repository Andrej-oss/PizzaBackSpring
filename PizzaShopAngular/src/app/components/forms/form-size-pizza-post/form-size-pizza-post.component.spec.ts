import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSizePizzaPostComponent } from './form-size-pizza-post.component';

describe('FormSizePizzaPostComponent', () => {
  let component: FormSizePizzaPostComponent;
  let fixture: ComponentFixture<FormSizePizzaPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormSizePizzaPostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormSizePizzaPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
