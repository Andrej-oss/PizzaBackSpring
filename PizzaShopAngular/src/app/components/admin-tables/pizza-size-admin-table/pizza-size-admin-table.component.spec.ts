import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PizzaSizeAdminTableComponent } from './pizza-size-admin-table.component';

describe('PizzaSizeAdminTableComponent', () => {
  let component: PizzaSizeAdminTableComponent;
  let fixture: ComponentFixture<PizzaSizeAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PizzaSizeAdminTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PizzaSizeAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
