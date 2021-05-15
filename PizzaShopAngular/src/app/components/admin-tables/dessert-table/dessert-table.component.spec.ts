import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DessertTableComponent } from './dessert-table.component';

describe('DessertTableComponent', () => {
  let component: DessertTableComponent;
  let fixture: ComponentFixture<DessertTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DessertTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DessertTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
