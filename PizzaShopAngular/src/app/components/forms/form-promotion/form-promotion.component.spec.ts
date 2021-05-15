import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormPromotionComponent } from './form-promotion.component';

describe('FormPromotionComponent', () => {
  let component: FormPromotionComponent;
  let fixture: ComponentFixture<FormPromotionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormPromotionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormPromotionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
