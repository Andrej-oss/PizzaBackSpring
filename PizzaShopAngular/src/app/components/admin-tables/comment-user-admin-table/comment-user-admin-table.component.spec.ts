import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentUserAdminTableComponent } from './comment-user-admin-table.component';

describe('CommentUserAdminTableComponent', () => {
  let component: CommentUserAdminTableComponent;
  let fixture: ComponentFixture<CommentUserAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentUserAdminTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentUserAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
