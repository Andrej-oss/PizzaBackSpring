import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackCardComponent } from './snack-card.component';

describe('SnackCardComponent', () => {
  let component: SnackCardComponent;
  let fixture: ComponentFixture<SnackCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnackCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SnackCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
