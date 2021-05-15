import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Cart} from '../../../components/models/Cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private baseUrl = '/api/cart';

  constructor(private httpClient: HttpClient) { }
  getAllCartsElements(id: number): Observable<Cart[]>{
    debugger;
    return this.httpClient.get<Cart[]>(this.baseUrl + `/${id}`);
  }
  savePizzaInCart(cart: Cart): Observable<Cart[]>{
    return this.httpClient.post<Cart[]>(this.baseUrl + `/${cart.userId}`, cart);
  }

  addAmountPizzaCart(id: number, price: number): Observable<boolean>{
    return  this.httpClient.post<boolean>(this.baseUrl + `/increment/${id}`, price);
  }
  removeAmountPizzaCart(id: number, price: number): Observable<boolean>{
    return this.httpClient.post<boolean>(this.baseUrl + `/decrement/${id}`, price);
  }
  deleteCart(id: number): Observable<boolean>{
    return this.httpClient.delete<boolean>(this.baseUrl + `/${id}`);
  }
}
