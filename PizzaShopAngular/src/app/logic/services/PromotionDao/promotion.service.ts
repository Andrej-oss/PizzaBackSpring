import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Promotion} from '../../../components/models/Promotion';
import {Observable} from 'rxjs';
import {APiURL} from "../../../config/urlConfig";

@Injectable({
  providedIn: 'root'
})
export class PromotionService {
  private baseUrl = APiURL.promotionURL;

  constructor(private httpClient: HttpClient) { }
  savePromotion(promotion: FormData, append: void): Observable<Promotion[]>{
    return this.httpClient.post<Promotion[]>(this.baseUrl, promotion);
  }
  getAllPromotions(): Observable<Promotion[]>{
    return this.httpClient.get<Promotion[]>(this.baseUrl);
  }
  deletePromotion(id: number): Observable<Promotion[]>{
    return this.httpClient.delete<Promotion[]>(this.baseUrl + `/${id}`);
  }
  updatePromotion(id: number, promotion: FormData, append: void): Observable<Promotion[]>{
   return this.httpClient.put<Promotion[]>(this.baseUrl + `/${id}`, promotion);
  }
}
