import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Snack} from '../../../components/models/Snack';


@Injectable({
  providedIn: 'root'
})
export class SnackService {
  private baseUrl = '/api/snack';
  constructor(private httpClient: HttpClient) { }
  saveSnack(formData: FormData, append: void): Observable<Snack[]>{
    return this.httpClient.post<Snack[]>(this.baseUrl, formData);
  }
  getAllSnacks(): Observable<Snack[]>{
    return this.httpClient.get<Snack[]>(this.baseUrl);
  }
  updateSnack(id: number, formData: FormData, append: void): Observable<Snack[]>{
    return this.httpClient.put<Snack[]>(this.baseUrl + `/${id}`, formData);
  }
  deleteSnack(id: number): Observable<Snack[]>{
    return this.httpClient.delete<Snack[]>(this.baseUrl + `/${id}`);
  }
}
