import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StarRatingVoteComponent } from './star-rating-vote.component';

describe('StarRatingComponent', () => {
  let component: StarRatingVoteComponent;
  let fixture: ComponentFixture<StarRatingVoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StarRatingVoteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StarRatingVoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
