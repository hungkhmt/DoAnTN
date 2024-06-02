import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
      return this.http.post<any>(this.url.concat('/transfer'), data, {headers});
    }

    withDraw(data: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      return this.http.post<any>(this.url.concat('/withdraw'), data, {headers});
    }

    deposit(data: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      return this.http.post<any>(this.url.concat('/deposit'), data, {headers});
    }
    
    getAllTransaction(accId: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      return this.http.get(`${this.url.concat('/transaction-by-accountId')}/${accId}`, {headers});
    }

    getAllTransactionByMonth(month: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      let params = new HttpParams().set('month', month);
      return this.http.get(this.url.concat('/listAllTransaction'), {params, headers});
    }

    getAllTransactionSourceId(accId: any, mounth: number): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      let params = new HttpParams().set('mounth', mounth);
      return this.http.get(`${this.url.concat('/sourceId')}/${accId}`, {params, headers});
    }

    getAllTransactionDestinationId(accId: any, mounth: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      let params = new HttpParams().set('mounth', mounth);
      return this.http.get(`${this.url.concat('/destinationId')}/${accId}`, {params, headers});
    }
}