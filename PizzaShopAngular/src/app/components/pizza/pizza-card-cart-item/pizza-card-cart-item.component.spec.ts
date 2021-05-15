import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PizzaCardCartItemComponent } from './pizza-card-cart-item.component';

describe('PizzaCardCartItemComponent', () => {
  let component: PizzaCardCartItemComponent;
  let fixture: ComponentFixture<PizzaCardCartItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PizzaCardCartItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PizzaCardCartItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
