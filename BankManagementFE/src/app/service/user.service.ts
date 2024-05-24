import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})

export class UserService {
    url = 'http://localhost:8081/api/user';

    constructor(private http: HttpClient) {}

    getAllUserWithRole(): Observable<any>{
      return this.http.get(this.url.concat('/ad/role'));
    }

    getUserById(id: any): Observable<any>{
        return this.http.get<User>(`${this.url}/${id}`);
    }
}