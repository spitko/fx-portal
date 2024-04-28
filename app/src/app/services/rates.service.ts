import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ExchangeRate } from '../models/ExchangeRate';

const baseUrl = 'http://localhost:8080/api/rates';

@Injectable({
  providedIn: 'root',
})
export class RatesService {
  private http = inject(HttpClient);

  getCurrent(): Observable<ExchangeRate[]> {
    return this.http.get<ExchangeRate[]>(`${baseUrl}/current`);
  }
}
