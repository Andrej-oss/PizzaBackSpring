import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnacksAdminTableComponent } from './snacks-admin-table.component';

describe('SnacksAdminTableComponent', () => {
  let component: SnacksAdminTableComponent;
  let fixture: ComponentFixture<SnacksAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SnacksAdminTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SnacksAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
