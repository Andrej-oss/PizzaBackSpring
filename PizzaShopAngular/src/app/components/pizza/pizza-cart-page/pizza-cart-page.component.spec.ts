import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PizzaCartPageComponent } from './pizza-cart-page.component';

describe('PizzaCartPageComponent', () => {
  let component: PizzaCartPageComponent;
  let fixture: ComponentFixture<PizzaCartPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PizzaCartPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PizzaCartPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
