import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinkPageComponent } from './drink-page.component';

describe('DrinkPageComponent', () => {
  let component: DrinkPageComponent;
  let fixture: ComponentFixture<DrinkPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrinkPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinkPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
