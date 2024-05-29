import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class TranferService {
    url = 'http://localhost:8888/api/v1/transaction';

    constructor(private http: HttpClient, private authService: AuthService) {}

    tranferMoney(data: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      return this.http.post<any>('http://localhost:8888/api/v1/transaction/transfer', data, {headers});
    }
    
}