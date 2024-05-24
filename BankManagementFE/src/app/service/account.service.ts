import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})

export class AccountService {
    url = 'http://localhost:8080/api/account';

    constructor(private http: HttpClient) {}

    getAllUserWithRole(): Observable<any>{
      return this.http.get(this.url.concat('/ad/role'));
    }

    getUserById(id: any): Observable<any>{
        return this.http.get<User>(`${this.url}/${id}`);
    }
}