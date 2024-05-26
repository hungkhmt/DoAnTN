import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TranferService {
    url = 'http://localhost:9000/api/v1/transaction';

    constructor(private http: HttpClient) {}

    tranferMoney(data: any): Observable<any> {
        // return this.http.post<any>(`${this.url.concat('/transfer')}`, data);
        return this.http.post<any>('http://localhost:9000/api/v1/transaction/transfer', data);

    }
    
}