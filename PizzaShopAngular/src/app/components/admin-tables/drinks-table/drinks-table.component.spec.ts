import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinksTableComponent } from './drinks-table.component';

describe('DrinksTableComponent', () => {
  let component: DrinksTableComponent;
  let fixture: ComponentFixture<DrinksTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrinksTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinksTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
