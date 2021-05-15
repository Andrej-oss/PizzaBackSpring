import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Voice} from '../../../components/models/Voice';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VoiceService {
  private baseURL = '/api/voice/';
  constructor(private httpClient: HttpClient) { }
  saveVoice(id: number, voice: Voice): Observable<Voice[]>{
    return this.httpClient.post<Voice[]>(this.baseURL + `${id}`, voice);
  }
  deleteVoiceComment(id: number): Observable<boolean>{
    return this.httpClient.delete<boolean>(this.baseURL + `${id}`);
  }
}
