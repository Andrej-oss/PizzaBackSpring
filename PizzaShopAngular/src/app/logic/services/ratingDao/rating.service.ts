import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Rating} from '../../../components/models/Rating';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  private baseUrl = '/api';

  constructor(private httpClient: HttpClient) { }
  saveRating(rating: Rating, pizzaId: number): Observable<Rating[]>{
    return this.httpClient.post<Rating[]>(this.baseUrl + `/rating/${pizzaId}`, rating);
  }
}
