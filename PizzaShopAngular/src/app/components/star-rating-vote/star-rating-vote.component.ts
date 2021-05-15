import {Component, Input, OnInit} from '@angular/core';
import {ThemeObjectService} from '../../logic/theme-object/theme-object.service';
import {Rating} from '../models/Rating';
import {select, Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {RatingSelector} from '../../logic/store/selectors/PizzaSelector';
import {SnackBarComponent} from '../snack-bar/snack-bar-login/snack-bar.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {PizzaActionService} from "../../logic/store/actions/pizza/pizza-action.service";

@Component({
  selector: 'app-star-rating-vote',
  templateUrl: './star-rating-vote.component.html',
  styleUrls: ['./star-rating-vote.component.css']
})
export class StarRatingVoteComponent implements OnInit {
  @Input()
  pizzaId: number;
  @Input()
  pizzaRating: Rating[];
  rating: Rating;
  stars: number[] = [1, 2, 3, 4, 5];
  selectedValue: number;
  ratingByUser: Observable<Rating> = this.store$.pipe(select(RatingSelector));
  isRated: boolean;
  userRating: Rating;
  averageRating: number;
  constructor(private pizzaService: PizzaActionService,
              public themeObjectService: ThemeObjectService,
              private snackBar: MatSnackBar,
              private store$: Store) { }

  ngOnInit(): void {
    this.isVoted();
    this.getAverageRating();
  }

  countStar(star: number): void{
    this.selectedValue = star;
    this.rating = {value: star, userId: this.themeObjectService.data.value.userId};
    this.pizzaService.postRating(this.pizzaId, this.rating);
    this.themeObjectService.data.value.message = 'Thank you for your vote';
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 2000,
    });
    this.averageRating = this.averageRating + star;
    this.isRated = true;
  }
  isVoted(): void{
    this.userRating = this.pizzaRating.find(value => value.userId === this.themeObjectService.data.value.userId);
    debugger;
    !this.userRating ? this.isRated = false : this.isRated = true;
  }
  getAverageRating(): void{
    this.averageRating = this.pizzaRating.reduce((previousValue, currentValue) => previousValue + currentValue.value, 0);
    if (this.pizzaRating.length){
      this.averageRating = Math.round(this.averageRating / this.pizzaRating.length);
    }
  }
}
