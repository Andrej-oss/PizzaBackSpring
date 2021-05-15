import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnackPageComponent } from './snack-page.component';

describe('SnackPageComponent', () => {
  let component: SnackPageComponent;
  let fixture: ComponentFixture<SnackPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnackPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SnackPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
