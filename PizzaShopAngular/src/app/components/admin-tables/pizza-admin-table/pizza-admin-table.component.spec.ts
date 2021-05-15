import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PizzaAdminTableComponent } from './pizza-admin-table.component';

describe('PizzaAdminTableComponent', () => {
  let component: PizzaAdminTableComponent;
  let fixture: ComponentFixture<PizzaAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PizzaAdminTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PizzaAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
