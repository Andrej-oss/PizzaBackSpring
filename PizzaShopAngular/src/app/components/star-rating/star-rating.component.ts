import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.css']
})
export class StarRatingComponent implements OnInit {
  @Input()
  averageRating;
  stars: number[] = [1, 2, 3, 4, 5];
  constructor() { }

  ngOnInit(): void {
    console.log(this.averageRating);
  }
}
