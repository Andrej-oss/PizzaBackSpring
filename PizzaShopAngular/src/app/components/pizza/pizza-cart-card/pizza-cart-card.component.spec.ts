import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PizzaCartCardComponent } from './pizza-cart-card.component';

describe('PizzaCartCardComponent', () => {
  let component: PizzaCartCardComponent;
  let fixture: ComponentFixture<PizzaCartCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PizzaCartCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PizzaCartCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
