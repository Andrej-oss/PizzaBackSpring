import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Dessert} from '../../../components/models/Dessert';

@Injectable({
  providedIn: 'root'
})
export class DessertService {
  private baseUrl = '/api/dessert';

  constructor(private httpClient: HttpClient) { }
  saveDessert(formData: FormData, append: void): Observable<Dessert[]>{
    return this.httpClient.post<Dessert[]>(this.baseUrl, formData);
  }
  getAllDessert(): Observable<Dessert[]>{
    return this.httpClient.get<Dessert[]>(this.baseUrl);
  }
  updateDessert(id: number, formData: FormData, append: void): Observable<Dessert[]>{
    return this.httpClient.put<Dessert[]>(this.baseUrl + `/${id}`, formData);
  }
  deleteDessert(id: number): Observable<Dessert[]>{
    return this.httpClient.delete<Dessert[]>(this.baseUrl + `/${id}`);
  }
}
