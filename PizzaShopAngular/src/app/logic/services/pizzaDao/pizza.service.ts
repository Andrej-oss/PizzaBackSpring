import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Pizza} from '../../../components/models/Pizza';
import {PizzaPage} from "../../../components/models/PizzaPage";

@Injectable({
  providedIn: 'root'
})
export class PizzaService {
  private baseUrl = '/api/pizza';

  constructor(private httpClient: HttpClient) {
  }

  getAllPizza(): Observable<Pizza[]> {
    debugger;
    return this.httpClient.get<Pizza[]>(this.baseUrl);
  }
  getSortedPizzas(page: number, type: string, sort: string): Observable<PizzaPage>{
    return this.httpClient.get<PizzaPage>(this.baseUrl + '/sort' + `?page=${page}&type=${type}&sort=${sort}`);
  }
  savePizza(formData: FormData, append: void): Observable<Pizza[]> {
    return this.httpClient.post<Pizza[]>(this.baseUrl, formData);
  }

  upDatePizza(id: number, formData: FormData, append: void): Observable<Pizza[]> {
    debugger;
    return this.httpClient.put<Pizza[]>(this.baseUrl + `/${id}`, formData);
  }
  deletePizza(id: number): Observable<Pizza[]>{
    return this.httpClient.delete<Pizza[]>(this.baseUrl + `/${id}`);
  }
}
