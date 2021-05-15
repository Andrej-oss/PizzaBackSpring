import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ingredient} from '../../../components/models/Ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  private baseUrl = '/api/ingredient';

  constructor(private httpClient: HttpClient) { }

  saveIngredient(formData: FormData, append: void): Observable<Ingredient[]>{
    console.log(formData);
    return this.httpClient.post<Ingredient[]>(this.baseUrl, formData);
  }
  getAllIngredients(): Observable<Ingredient[]>{
    return this.httpClient.get<Ingredient[]>(this.baseUrl);
  }
  upDateIngredient(id: number, formData: FormData, append: void): Observable<Ingredient[]>{
    return this.httpClient.put<Ingredient[]>(this.baseUrl + `/${id}`, formData);
  }
  deleteIngredient(id: number): Observable<Ingredient[]>{
    return this.httpClient.delete<Ingredient[]>(this.baseUrl + `/${id}`);
  }
}
