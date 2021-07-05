import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Cart} from '../../../components/models/Cart';
import {APiURL} from "../../../config/urlConfig";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private baseUrl = APiURL.cartURL;
  private localStorageCart = APiURL.localStorageKey;
  cart: Cart[];


  constructor(private httpClient: HttpClient) { }
  getAllCartsElements(id: number): Observable<Cart[]>{
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
  getCartFromLocalStorage(): any {
    this.cart = [];
    for (const cartElement in localStorage) {
      if (cartElement.startsWith(APiURL.localStorageKey)) {
        this.cart.push(JSON.parse(localStorage.getItem(cartElement)));
      }
    }
    return this.cart;
  }

  deleteCartElementFromLocalStorage(description: string): Cart[] {
    if (!!description) {
      this.cart = this.getCartFromLocalStorage();
      if (this.cart.length) {
        const i = this.cart.findIndex(value => description === value.description);
        this.cart.splice(i, 1);
        localStorage.removeItem(`${APiURL.localStorageKey}_${description}`);
        return this.cart;
      }
    }
  }

  onIncCartElementInLocalStorage(description: string, elementPrice: number): Cart {
    let cart = {};
    if (description.length) {
      const item = JSON.parse(localStorage.getItem(`${APiURL.localStorageKey}_${description}`));
      cart = {...item, amount: item.amount + 1, price: elementPrice + item.price};
      localStorage.setItem(`${APiURL.localStorageKey}_${description}`, JSON.stringify(cart));
    }
    return JSON.parse(localStorage.getItem(`${APiURL.localStorageKey}_${description}`));
  }

  onDecCartElementInLocalStorage(description: string, elementPrice: number): Cart {
    let cart = {};
    if (description.length) {
      const item = JSON.parse(localStorage.getItem(`${APiURL.localStorageKey}_${description}`));
      cart = {...item, amount: item.amount - 1, price: item.price - elementPrice};
      localStorage.setItem(`${APiURL.localStorageKey}_${description}`, JSON.stringify(cart));
    }
    return JSON.parse(localStorage.getItem(`${APiURL.localStorageKey}_${description}`));
  }
}
