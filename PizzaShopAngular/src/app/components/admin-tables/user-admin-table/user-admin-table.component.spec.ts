import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAdminTableComponent } from './user-admin-table.component';

describe('UserAdminTableComponent', () => {
  let component: UserAdminTableComponent;
  let fixture: ComponentFixture<UserAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAdminTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
