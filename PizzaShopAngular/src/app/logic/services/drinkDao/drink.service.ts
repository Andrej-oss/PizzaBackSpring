import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Drink} from '../../../components/models/Drink';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DrinkService {
  private baseUrl = '/api/drink';

  constructor(private httpClient: HttpClient) { }
  saveDrink(formData: FormData, append: void): Observable<Drink[]>{
    return this.httpClient.post<Drink[]>(this.baseUrl, formData);
  }
  getAllDrinks(): Observable<Drink[]>{
    return this.httpClient.get<Drink[]>(this.baseUrl);
  }
  updateDrink(id: number, formData: FormData, append: void): Observable<Drink[]>{
    return this.httpClient.put<Drink[]>(this.baseUrl + `/${id}`, formData);
  }
  deleteDrink(id: number): Observable<Drink[]>{
    return this.httpClient.delete<Drink[]>(this.baseUrl + `/${id}`);
  }
}
